package com.example.spring.boot.use.minio.service.impl;

import com.example.spring.boot.use.minio.service.MinioService;
import com.example.spring.boot.use.minio.util.MinioUtil;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理-MinIO 服务实现类
 *
 * @author minus
 * @since 2024/5/4 17:53
 */
@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioUtil minioUtil;

    @Override
    public String uploadFile(String bucket, MultipartFile file) {
        return minioUtil.putObject(bucket, file);
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(String bucket, String fileName) {
        return minioUtil.getObject(bucket, fileName);
    }

    @Override
    public boolean deleteFile(String bucket, String fileName) {
        return minioUtil.removeObject(bucket, fileName);
    }

    @Override
    public List<Bucket> listBuckets() {
        return minioUtil.listBuckets();
    }

    @Override
    public boolean bucketExists(String bucket) {
        return minioUtil.bucketExists(bucket);
    }

    @Override
    public void createBucket(String bucket) {
        minioUtil.createBucket(bucket);
    }

    @Override
    public void removeBucket(String bucket) {
        minioUtil.removeBucket(bucket);
    }

    @Override
    public List<String> listObjects(String bucket) {
        List<Result<Item>> results = minioUtil.listObjects(bucket);
        List<String> fileNames = new ArrayList<>();
        for (Result<Item> result : results) {
            try {
                fileNames.add(result.get().objectName());
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                     XmlParserException e) {
                throw new RuntimeException(e);
            }
        }
        return fileNames;
    }

    @Override
    public String fileUrl(String bucket, String fileName) {
        return minioUtil.getObjectUrl(bucket, fileName);
    }

}
