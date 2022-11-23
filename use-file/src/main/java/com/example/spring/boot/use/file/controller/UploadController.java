package com.example.spring.boot.use.file.controller;

import com.example.spring.boot.use.file.common.ApiConst;
import com.example.spring.boot.use.file.model.FileInfoVO;
import com.example.spring.boot.use.file.model.ResultVO;
import com.example.spring.boot.use.file.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 上传文件
 *
 * @author minus
 * @since 2022/11/23 21:33
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/upload")
@Api(tags = "上传文件")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/file")
    @ApiOperation(value = "API-01-单文件上传")
    public ResultVO<FileInfoVO> uploadFile(
            @ApiParam("文件") @RequestPart MultipartFile file
    ) {
        return uploadService.uploadFile(file);
    }

    @PostMapping("/files")
    @ApiOperation(value = "API-02-多文件上传")
    public ResultVO<List<FileInfoVO>> uploadFiles(
            @ApiParam("文件1") @RequestPart MultipartFile file1,
            @ApiParam("文件2") @RequestPart MultipartFile file2
    ) {
        return uploadService.uploadFiles(file1, file2);
    }

}
