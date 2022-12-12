package com.example.spring.boot.use.file.service;

import com.example.spring.boot.use.file.model.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传
 *
 * @author minus
 * @since 2022/11/23 21:38
 */
public interface UploadService {

    FileInfoVO uploadFile(MultipartFile file);

    List<FileInfoVO> uploadFiles(MultipartFile file1, MultipartFile file2);

}
