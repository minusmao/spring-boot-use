package com.example.spring.boot.use.file.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载
 *
 * @author minus
 * @since 2022/11/23 23:43
 */
public interface DownloadService {

    void downloadFile(String filename, HttpServletResponse response);

    ResponseEntity<InputStreamResource> downloadFile(String filename);

    void downloadResource(Integer mode, HttpServletResponse response);

}
