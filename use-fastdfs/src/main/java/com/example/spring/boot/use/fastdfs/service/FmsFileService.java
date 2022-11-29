package com.example.spring.boot.use.fastdfs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.fastdfs.entity.FmsFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring.boot.use.fastdfs.model.ResultVO;
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

    ResultVO<FmsFile> uploadFile(MultipartFile file);

    ResultVO<FmsFile> removeFileById(String id);

    ResultVO<FmsFile> getFileById(String id);

    ResultVO<Page<FmsFile>> pageFile(Page<FmsFile> page);

    ResponseEntity<byte[]> downloadFileById(String id);

}
