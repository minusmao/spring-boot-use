package com.example.spring.boot.use.mongo.service;

import com.example.spring.boot.use.mongo.entity.FileDocument;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储（大于16MB）
 *
 * @author minus
 * @since 2023/05/24 20:57
 */
public interface FileDocumentService {

    /**
     * 新增文件
     * @param file 文件
     * @return 文件信息
     */
    FileDocument saveFile(MultipartFile file);

    /**
     * 删除文件
     *
     * @param id 文件id
     */
    void removeFile(String id);

    /**
     * 查询文件信息
     *
     * @param id 文件id
     * @return 文件信息
     */
    FileDocument findFile(String id);

    /**
     * 下载文件
     *
     * @param id 文件id
     * @return 文件
     */
    ResponseEntity<GridFsResource> downloadFile(String id);

}
