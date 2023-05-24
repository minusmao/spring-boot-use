package com.example.spring.boot.use.mongo.service;

import com.example.spring.boot.use.mongo.entity.FileModel;
import com.mongodb.client.result.UpdateResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储（小于16MB）
 *
 * @author minus
 * @since 2023/05/19 20:57
 */
public interface FileModelService {

    /**
     * 新增文件
     *
     * @param file 文件
     * @return 文件信息
     */
    FileModel saveFile(MultipartFile file);

    /**
     * 删除文件
     *
     * @param id 文件id
     */
    void removeFile(String id);

    /**
     * 更新文件名称
     *
     * @param id   文件id
     * @param name 文件名称
     * @return 更新情况
     */
    UpdateResult updateFileName(String id, String name);

    /**
     * 查询文件
     *
     * @param id 文件id
     * @return 文件信息
     */
    FileModel findFile(String id);

    /**
     * 下载文件
     *
     * @param id 文件id
     * @return 文件
     */
    ResponseEntity<byte[]> downloadFile(String id);

}
