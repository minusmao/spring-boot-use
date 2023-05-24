package com.example.spring.boot.use.mongo.service.impl;

import com.example.spring.boot.use.mongo.common.exception.OperationFailureException;
import com.example.spring.boot.use.mongo.common.exception.ResourceNotFoundException;
import com.example.spring.boot.use.mongo.entity.FileModel;
import com.example.spring.boot.use.mongo.repository.FileModelRepository;
import com.example.spring.boot.use.mongo.service.FileModelService;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 文件存储（小于16MB）
 *
 * @author minus
 * @since 2023/05/19 20:57
 */
@Service
public class FileModelServiceImpl implements FileModelService {

    @Autowired
    private FileModelRepository fileModelRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增文件<br>
     * <a href="https://www.cnblogs.com/dreamzy996/p/16807073.html">获取文件MD5值</a>
     *
     * @param file 文件
     * @return 文件信息
     */
    @Override
    public FileModel saveFile(MultipartFile file) {
        FileModel fileModel = new FileModel();
        fileModel.setName(file.getOriginalFilename());
        fileModel.setContentType(file.getContentType());
        fileModel.setSize(file.getSize());
        fileModel.setUploadTime(LocalDateTime.now());
        // 文件摘要和内容
        try {
            byte[] fileBytes = file.getBytes();
            fileModel.setMd5(DigestUtils.md5DigestAsHex(fileBytes));
            fileModel.setContent(new Binary(fileBytes));
        } catch (IOException e) {
            e.printStackTrace();
            throw new OperationFailureException("文件流错误");
        }
        fileModelRepository.insert(fileModel);

        return fileModel;
    }

    /**
     * 删除文件
     *
     * @param id 文件id
     */
    @Override
    public void removeFile(String id) {
        fileModelRepository.deleteById(id);
    }

    /**
     * 更新文件名称
     *
     * @param id   文件id
     * @param name 文件名称
     */
    @Override
    public UpdateResult updateFileName(String id, String name) {
        return mongoTemplate.upsert(
                new Query(Criteria.where("_id").is(id)),
                new Update().set("name", name),
                FileModel.class
        );
    }

    /**
     * 查询文件
     *
     * @param id 文件id
     * @return 文件信息
     */
    @Override
    public FileModel findFile(String id) {
        Optional<FileModel> fileModelOptional = fileModelRepository.findById(id);
        if (fileModelOptional.isEmpty()) {
            throw new ResourceNotFoundException("文件信息未找到");
        }
        return fileModelOptional.get();
    }

    /**
     * 下载文件
     *
     * @param id 文件id
     * @return 文件
     */
    @Override
    public ResponseEntity<byte[]> downloadFile(String id) {
        // 查找文件
        Optional<FileModel> fileModelOptional = fileModelRepository.findById(id);
        if (fileModelOptional.isEmpty()) {
            throw new ResourceNotFoundException("文件信息未找到");
        }
        FileModel fileModel = fileModelOptional.get();
        // 文件名URL编码
        String filenameEncode = URLEncoder.encode(fileModel.getName(), StandardCharsets.UTF_8);
        // 响应
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentDispositionFormData("attachment", filenameEncode);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    // 二进制流
        headers.setContentLength(fileModel.getSize());
        return ResponseEntity.ok().headers(headers).body(fileModel.getContent().getData());
    }

}
