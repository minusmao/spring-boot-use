package com.example.spring.boot.use.minio.util;

import com.example.spring.boot.use.minio.common.exception.ResourceNotFoundException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * MinIO 工具类
 *
 * @author minus
 * @since 2024/5/4 14:01
 */
@Component
@Slf4j
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    /**
     * 文件上传
     *
     * @param bucket 桶
     * @param file   文件
     */
    public String putObject(String bucket, MultipartFile file) {
        String objectUrl;
        // 如果 Bucket 不存在，创建
        createBucket(bucket);
        // 开始上传
        try (InputStream inputStream = file.getInputStream()) {
            // 上传文件的参数
            String fileName = file.getOriginalFilename();
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
            //生产外链
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .method(Method.GET)
                    .build();
            objectUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (IOException | ServerException | ErrorResponseException | InsufficientDataException |
                 InternalException | InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException |
                 XmlParserException e) {
            throw new RuntimeException("上传至 MinIO 失败", e);
        }

        return StringUtils.substringBefore(objectUrl, "?");
    }

    /**
     * 文件下载
     *
     * @param bucket   桶
     * @param fileName 文件名称
     */
    public ResponseEntity<InputStreamResource> getObject(String bucket, String fileName) {
        // 文件对象查询参数
        GetObjectArgs objectArgs = GetObjectArgs.builder()
                .bucket(bucket)
                .object(fileName)
                .build();
        // 下载文件对象
        GetObjectResponse object;
        try {
            object = minioClient.getObject(objectArgs);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            log.error("下载文件对象失败", e);
            throw new ResourceNotFoundException("文件不存在");
        }
        // 处理响应头
        Headers objectHeaders = object.headers();
        HttpHeaders headers = new HttpHeaders();
        objectHeaders.names().forEach(name -> headers.set(name, objectHeaders.get(name)));
        headers.setContentDispositionFormData("attachment", UriUtils.encode(fileName, "UTF-8"));
        InputStreamResource inputStreamResource = new InputStreamResource(object);
        return ResponseEntity
                .ok()
                .headers(headers)
                // 也可在headers.setContentType()设置
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStreamResource);
    }

    /**
     * 文件删除
     *
     * @param bucket   桶
     * @param fileName 文件名称
     */
    public boolean removeObject(String bucket, String fileName) {
        RemoveObjectArgs objectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(fileName)
                .build();
        try {
            minioClient.removeObject(objectArgs);
            return true;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException("删除 MinIO 文件失败", e);
        }
    }

    /**
     * 查看所有桶
     */
    public List<Bucket> listBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            log.error("访问 MinIO 失败", e);
            throw new RuntimeException("访问 MinIO 失败", e);
        }
    }

    /**
     * 查看桶是否存在
     *
     * @param bucket 桶
     */
    public boolean bucketExists(String bucket) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            log.error("查看桶，访问 MinIO 失败", e);
            throw new RuntimeException("访问 MinIO 失败", e);
        }
    }

    /**
     * 创建桶
     *
     * @param bucket 桶
     */
    public void createBucket(String bucket) {
        try {
            if (!bucketExists(bucket)) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            log.error("创建桶，访问 MinIO 失败", e);
            throw new RuntimeException("访问 MinIO 失败", e);
        }
    }

    /**
     * 删除桶
     *
     * @param bucket 桶
     */
    public void removeBucket(String bucket) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            log.error("删除桶，访问 MinIO 失败", e);
            throw new RuntimeException("访问 MinIO 失败", e);
        }
    }

    /**
     * 查看桶中所有文件
     *
     * @param bucket 桶
     */
    public List<Result<Item>> listObjects(String bucket) {
        ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder().bucket(bucket).build();
        List<Result<Item>> results = new ArrayList<>();
        for (Result<Item> result : minioClient.listObjects(listObjectsArgs)) {
            results.add(result);
        }
        return results;
    }

    /**
     * 获取文件外链
     * @param bucket 桶
     * @param fileName 文件名称
     */
    public String getObjectUrl(String bucket, String fileName) {
        if (bucketExists(bucket)) {
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .method(Method.GET)
                    .build();
            try {
                String objectUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
                return StringUtils.substringBefore(objectUrl, "?");
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException |
                     ServerException e) {
                log.error("获取文件外链，访问 MinIO 失败", e);
                throw new RuntimeException(e);
            }
        } else {
            throw new ResourceNotFoundException("桶不存在");
        }
    }

}
