package com.example.spring.boot.use.mongo.service.impl;

import com.example.spring.boot.use.mongo.common.exception.OperationFailureException;
import com.example.spring.boot.use.mongo.common.exception.ResourceNotFoundException;
import com.example.spring.boot.use.mongo.entity.FileDocument;
import com.example.spring.boot.use.mongo.repository.FileDocumentRepository;
import com.example.spring.boot.use.mongo.service.FileDocumentService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 文件存储（大于16MB）
 *
 * @author minus
 * @since 2023/05/24 20:57
 */
@Service
public class FileDocumentServiceImpl implements FileDocumentService {

    @Autowired
    private FileDocumentRepository fileDocumentRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 新增文件
     *
     * @param file 文件
     * @return 文件信息
     */
    @Override
    public FileDocument saveFile(MultipartFile file) {
        FileDocument fileDocument = new FileDocument();
        fileDocument.setName(file.getOriginalFilename());
        fileDocument.setContentType(file.getContentType());
        fileDocument.setSize(file.getSize());
        fileDocument.setUploadTime(LocalDateTime.now());
        // 文件摘要和内容
        try (InputStream inputStream = file.getInputStream()) {
            // 这里暂时去掉md5值的计算，因为InputStream不能重复读，所以在计算md5值后再保存到GridFS的文件就为空了
            // fileDocument.setMd5(DigestUtils.md5DigestAsHex(inputStream));
            // 保存文件内容到GridFS中
            String gridFSId = gridFsTemplate.store(inputStream, fileDocument.getName(), fileDocument.getContentType())
                    .toHexString();
            fileDocument.setGridFSId(gridFSId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new OperationFailureException("文件流错误");
        }
        fileDocumentRepository.insert(fileDocument);

        return fileDocument;
    }

    /**
     * 删除文件
     *
     * @param id 文件id
     */
    @Override
    public void removeFile(String id) {
        // 查找文件信息
        Optional<FileDocument> fileModelOptional = fileDocumentRepository.findById(id);
        if (fileModelOptional.isEmpty()) {
            throw new ResourceNotFoundException("文件信息未找到");
        }
        FileDocument fileDocument = fileModelOptional.get();
        // 删除GridFS中文件内容
        Query deleteFileQuery = new Query().addCriteria(Criteria.where("_id").is(fileDocument.getGridFSId()));
        gridFsTemplate.delete(deleteFileQuery);
        // 删除文件信息
        fileDocumentRepository.deleteById(id);
    }

    /**
     * 查询文件信息
     *
     * @param id 文件id
     * @return 文件信息
     */
    @Override
    public FileDocument findFile(String id) {
        Optional<FileDocument> fileModelOptional = fileDocumentRepository.findById(id);
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
    public ResponseEntity<GridFsResource> downloadFile(String id) {
        // 查找文件信息
        Optional<FileDocument> fileModelOptional = fileDocumentRepository.findById(id);
        if (fileModelOptional.isEmpty()) {
            throw new ResourceNotFoundException("文件信息未找到");
        }
        FileDocument fileDocument = fileModelOptional.get();
        // 文件名URL编码
        String filenameEncode = URLEncoder.encode(fileDocument.getName(), StandardCharsets.UTF_8);
        // 从GridFS中查找文件内容资源
        Query gridQuery = new Query().addCriteria(Criteria.where("_id").is(fileDocument.getGridFSId()));
        GridFSFile fsFile = gridFsTemplate.findOne(gridQuery);
        if (fsFile == null) {
            throw new ResourceNotFoundException("文件资源未找到");
        }
        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(fsFile.getObjectId());
        GridFsResource resource = new GridFsResource(fsFile, downloadStream);
        // 响应
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentDispositionFormData("attachment", filenameEncode);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    // 二进制流
        headers.setContentLength(fileDocument.getSize());
        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
