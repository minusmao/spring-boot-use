package com.example.spring.boot.use.fastdfs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.fastdfs.common.exception.OperationFailureException;
import com.example.spring.boot.use.fastdfs.common.exception.ResourceNotFoundException;
import com.example.spring.boot.use.fastdfs.entity.FmsFile;
import com.example.spring.boot.use.fastdfs.mapper.FmsFileMapper;
import com.example.spring.boot.use.fastdfs.model.ResultVO;
import com.example.spring.boot.use.fastdfs.service.FmsFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring.boot.use.fastdfs.util.FastDFSUtil;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 文件管理-文件 服务实现类
 * </p>
 *
 * @author minus
 * @since 2022-11-29
 */
@Service
public class FmsFileServiceImpl extends ServiceImpl<FmsFileMapper, FmsFile> implements FmsFileService {

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Override
    public ResultVO<FmsFile> uploadFile(MultipartFile file) {
        // 上传文件
        StorePath storePath;
        try {
            storePath = fastDFSUtil.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new OperationFailureException("文件上传失败");
        }
        // 保存文件信息
        FmsFile fileInfo = new FmsFile();
        fileInfo.setSize((int) file.getSize());
        fileInfo.setName(fastDFSUtil.getFilename(storePath));
        fileInfo.setType(file.getContentType());
        fileInfo.setPath(storePath.getFullPath());
        save(fileInfo);

        return ResultVO.suc(fileInfo);
    }

    @Override
    public ResultVO<FmsFile> removeFileById(String id) {
        // 查询文件信息
        FmsFile file = getById(id);
        if (file == null) {
            throw new ResourceNotFoundException("文件信息未找到");
        }

        // 删除文件
        try {
            fastDFSUtil.deleteFile(file.getPath());
            removeById(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new OperationFailureException("文件删除失败");
        }

        return ResultVO.suc();
    }

    @Override
    public ResultVO<FmsFile> getFileById(String id) {
        FmsFile file = getById(id);
        if (file == null) {
            throw new ResourceNotFoundException("文件信息未找到");
        } else {
            file.setUrl(fastDFSUtil.getRestAccessUrl(file.getPath()));
        }

        return ResultVO.suc(file);
    }

    @Override
    public ResultVO<Page<FmsFile>> pageFile(Page<FmsFile> page) {
        // 分页查询
        Page<FmsFile> filePage = page(page);
        // 添加url
        filePage.getRecords().forEach(file -> file.setUrl(fastDFSUtil.getRestAccessUrl(file.getPath())));
        return ResultVO.suc(filePage);
    }

    @Override
    public ResponseEntity<byte[]> downloadFileById(String id) {
        FmsFile file = getById(id);
        if (file == null) {
            throw new ResourceNotFoundException("文件信息未找到");
        }
        // 下载文件
        byte[] fileBytes = fastDFSUtil.downloadFile(file.getPath());
        // 响应
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentDispositionFormData("attachment", file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    // 二进制流
        headers.setContentLength(fileBytes.length);
        return ResponseEntity.ok().headers(headers).body(fileBytes);
    }

    @Override
    public ResultVO<FmsFile> saveContentFile(String content, String extension) {
        // 保存文本文件
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        StorePath storePath = fastDFSUtil.uploadFile(contentBytes, extension);
        // 保存文件信息
        FmsFile fileInfo = new FmsFile();
        fileInfo.setSize(contentBytes.length);
        fileInfo.setName(fastDFSUtil.getFilename(storePath));
        fileInfo.setType("text/plain");
        fileInfo.setPath(storePath.getFullPath());
        save(fileInfo);

        return ResultVO.suc(fileInfo);
    }

}
