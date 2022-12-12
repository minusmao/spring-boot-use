package com.example.spring.boot.use.ftp.service.impl;

import com.example.spring.boot.use.ftp.common.exception.ResourceNotFoundException;
import com.example.spring.boot.use.ftp.model.FileInfoVO;
import com.example.spring.boot.use.ftp.service.FtpService;
import com.example.spring.boot.use.ftp.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * FTP
 *
 * @author minus
 * @since 2022/11/26 21:07
 */
@Service
public class FtpServiceImpl implements FtpService {

    @Autowired
    private FtpUtil ftpUtil;

    @Override
    public ResponseEntity<Resource> downloadFile(String filename) {
        // 拿到文件resource
        Resource resource = ftpUtil.getFptResource(filename);
        // 拿到文件数据
        InputStream inputStream;
        long contentLength;
        try {
            inputStream = resource.getInputStream();
            contentLength = resource.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("文件不存在");
        }
        // 响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", resource.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(contentLength)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(inputStream));
    }

    @Override
    public FileInfoVO uploadFile(MultipartFile file) {
        // 上传到FTP
        String filename = ftpUtil.uploadToFtp(file);
        // 获取文件信息
        FileInfoVO fileInfo = new FileInfoVO();
        fileInfo.setFileName(filename);
        fileInfo.setContentType(file.getContentType());
        fileInfo.setFileSize(file.getSize());

        return fileInfo;
    }

}
