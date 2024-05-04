package com.example.spring.boot.use.minio.controller;

import com.example.spring.boot.use.minio.common.ApiConst;
import com.example.spring.boot.use.minio.model.ResultVO;
import com.example.spring.boot.use.minio.service.MinioService;
import io.minio.messages.Bucket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件管理-MinIO
 *
 * @author minus
 * @since 2024/5/4 17:53
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/file")
@Api(tags = "文件管理-MinIO")
public class MinioController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/file/{bucket}")
    @ApiOperation(value = "API-01-上传文件")
    public ResultVO<String> uploadFile(
            @ApiParam("桶") @PathVariable String bucket,
            @ApiParam("文件") @RequestPart MultipartFile file
    ) {
        return ResultVO.suc(minioService.uploadFile(bucket, file));
    }

    @GetMapping("/file/{bucket}/{fileName}")
    @ApiOperation(value = "API-02-下载文件")
    public ResponseEntity<InputStreamResource> downloadFile(
            @ApiParam("桶") @PathVariable String bucket,
            @ApiParam("文件名") @PathVariable String fileName
    ) {
        return minioService.downloadFile(bucket, fileName);
    }

    @DeleteMapping("/file/{bucket}")
    @ApiOperation(value = "API-03-删除文件")
    public ResultVO<Boolean> deleteFile(
            @ApiParam("桶") @PathVariable String bucket,
            @ApiParam("文件名") @RequestParam String fileName
    ) {
        return ResultVO.suc(minioService.deleteFile(bucket, fileName));
    }

    @ApiOperation(value = "API-04-查看所有桶")
    @GetMapping("/bucket")
    public ResultVO<List<String>> listBuckets() {
        return ResultVO.suc(minioService.listBuckets().stream().map(Bucket::name).toList());
    }

    @ApiOperation(value = "API-05-查看桶是否存在")
    @GetMapping("/bucket/{bucket}")
    public ResultVO<Boolean> bucketExists(
            @ApiParam("桶") @PathVariable String bucket
    ) {
        return ResultVO.suc(minioService.bucketExists(bucket));
    }

    @ApiOperation(value = "API-06-创建桶")
    @PostMapping("/bucket/{bucket}")
    public ResultVO<Boolean> createBucket(
            @ApiParam("桶") @PathVariable String bucket
    ) {
        minioService.createBucket(bucket);
        return ResultVO.suc(true);
    }

    @ApiOperation(value = "API-07-删除桶")
    @DeleteMapping("/bucket/{bucket}")
    public ResultVO<Boolean> removeBucket(
            @ApiParam("桶") @PathVariable String bucket
    ) {
        minioService.removeBucket(bucket);
        return ResultVO.suc(true);
    }

    @ApiOperation(value = "API-08-查看桶中的所有文件")
    @GetMapping("/bucket/file")
    public ResultVO<List<String>> listObjects(
            @ApiParam("桶") @RequestParam String bucket
    ) {
        return ResultVO.suc(minioService.listObjects(bucket));
    }

    @ApiOperation(value = "API-09-查看文件访问外链")
    @GetMapping("/file/url")
    public ResultVO<String> fileUrl(
            @ApiParam("桶") @RequestParam String bucket,
            @ApiParam("文件名") @RequestParam String fileName
    ) {
        return ResultVO.suc(minioService.fileUrl(bucket, fileName));
    }

}
