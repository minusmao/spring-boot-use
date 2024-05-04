package com.example.spring.boot.use.minio.service;

import io.minio.messages.Bucket;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件管理-MinIO 服务类
 *
 * @author minus
 * @since 2024/5/4 17:53
 */
public interface MinioService {

    String uploadFile(String bucket, MultipartFile file);

    ResponseEntity<InputStreamResource> downloadFile(String bucket, String fileName);

    boolean deleteFile(String bucket, String fileName);

    List<Bucket> listBuckets();

    boolean bucketExists(String bucket);

    void createBucket(String bucket);

    void removeBucket(String bucket);

    List<String> listObjects(String bucket);

    String fileUrl(String bucket, String fileName);

}
