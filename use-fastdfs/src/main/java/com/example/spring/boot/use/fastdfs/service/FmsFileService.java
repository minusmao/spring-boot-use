package com.example.spring.boot.use.fastdfs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.fastdfs.entity.FmsFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件管理-文件 服务类
 * </p>
 *
 * @author minus
 * @since 2022-11-29
 */
public interface FmsFileService extends IService<FmsFile> {

    FmsFile uploadFile(MultipartFile file);

    void removeFileById(String id);

    FmsFile getFileById(String id);

    Page<FmsFile> pageFile(Page<FmsFile> page);

    ResponseEntity<byte[]> downloadFileById(String id);

    FmsFile saveContentFile(String content, String extension);

}
