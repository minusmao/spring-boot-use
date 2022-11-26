package com.example.spring.boot.use.ftp.service;

import com.example.spring.boot.use.ftp.model.FileInfoVO;
import com.example.spring.boot.use.ftp.model.ResultVO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * FTP
 *
 * @author minus
 * @since 2022/11/26 21:06
 */
public interface FtpService {

    ResponseEntity<Resource> downloadFile(String filename);

    ResultVO<FileInfoVO> uploadFile(MultipartFile file);

}
