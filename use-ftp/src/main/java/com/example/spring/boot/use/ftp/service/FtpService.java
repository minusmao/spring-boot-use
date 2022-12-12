package com.example.spring.boot.use.ftp.service;

import com.example.spring.boot.use.ftp.model.FileInfoVO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * FTP
 *
 * @author minus
 * @since 2022/11/26 21:06
 */
public interface FtpService {

    ResponseEntity<Resource> downloadFile(String filename);

    FileInfoVO uploadFile(MultipartFile file);

}
