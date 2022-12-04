package com.example.spring.boot.use.file.controller;

import com.example.spring.boot.use.file.common.ApiConst;
import com.example.spring.boot.use.file.service.DownloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载
 *
 * @author minus
 * @since 2022/11/23 23:44
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/download")
@Api(tags = "文件下载")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @GetMapping("/file1")
    @ApiOperation(value = "API-01-文件下载方式1")
    public void downloadFile1(
            @ApiParam("文件名") @RequestParam String filename,
            HttpServletResponse response
    ) {
        downloadService.downloadFile(filename, response);
    }

    @GetMapping("/file2")
    @ApiOperation(value = "API-02-文件下载方式2")
    public ResponseEntity<InputStreamResource> downloadFile(
            @ApiParam("文件名") @RequestParam String filename
    ) {
        return downloadService.downloadFile(filename);
    }

    @GetMapping("/resource")
    @ApiOperation(value = "API-03-文件下载方式3")
    public void downloadResource(
            @ApiParam("资源匹配模式 1-file模式 2-classpath模式") @RequestParam Integer mode,
            HttpServletResponse response
    ) {
        downloadService.downloadResource(mode, response);
    }

}
