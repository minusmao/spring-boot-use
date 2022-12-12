package com.example.spring.boot.use.ftp.controller;

import com.example.spring.boot.use.ftp.common.ApiConst;
import com.example.spring.boot.use.ftp.model.FileInfoVO;
import com.example.spring.boot.use.ftp.model.ResultVO;
import com.example.spring.boot.use.ftp.service.FtpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * FTP
 *
 * @author minus
 * @since 2022/11/26 20:27
 */
@RestController
@RequestMapping(ApiConst.API_URL)
@Api(tags = "FTP")
public class FtpController {

    @Autowired
    private FtpService ftpService;

    @GetMapping("/download")
    @ApiOperation(value = "API-01-下载文件")
    public ResponseEntity<Resource> downloadFile(
            @ApiParam("文件名") @RequestParam String filename
    ) {
        return ftpService.downloadFile(filename);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "API-02-上传文件")
    public ResultVO<FileInfoVO> uploadFile(
            @ApiParam("文件") @RequestPart MultipartFile file
    ) {
        return ResultVO.suc(ftpService.uploadFile(file));
    }

}
