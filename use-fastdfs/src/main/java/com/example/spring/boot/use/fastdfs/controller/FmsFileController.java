package com.example.spring.boot.use.fastdfs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.fastdfs.common.ApiConst;
import com.example.spring.boot.use.fastdfs.entity.FmsFile;
import com.example.spring.boot.use.fastdfs.model.ResultVO;
import com.example.spring.boot.use.fastdfs.service.FmsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件管理-文件 前端控制器
 * </p>
 *
 * @author minus
 * @since 2022-11-29
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/file")
@Api(tags = "文件管理-文件")
public class FmsFileController {

    @Autowired
    private FmsFileService fmsFileService;

    @PostMapping("/upload")
    @ApiOperation(value = "API-01-上传文件")
    public ResultVO<FmsFile> uploadFile(
            @ApiParam("文件") @RequestPart MultipartFile file
    ) {
        return fmsFileService.uploadFile(file);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "API-02-删除文件")
    public ResultVO<FmsFile> removeFileById(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return fmsFileService.removeFileById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API-03-查询文件")
    public ResultVO<FmsFile> getFileById(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return fmsFileService.getFileById(id);
    }

    @GetMapping("/{current}/{size}")
    @ApiOperation(value = "API-04-查询文件分页")
    public ResultVO<Page<FmsFile>> pageFile(
            @ApiParam("页码") @PathVariable Long current,
            @ApiParam("页大小") @PathVariable Long size
    ) {
        return fmsFileService.pageFile(new Page<>(current, size));
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "API-05-下载文件")
    public ResponseEntity<byte[]> downloadFileById(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return fmsFileService.downloadFileById(id);
    }

    @PostMapping("/content")
    @ApiOperation(value = "API-06-保存文件（文本文档）")
    public ResultVO<FmsFile> saveContentFile(
            @ApiParam("文本内容") @RequestParam String content,
            @ApiParam("文件扩展名（默认为txt）") @RequestParam(defaultValue = "txt") String extension
    ) {
        return fmsFileService.saveContentFile(content, extension);
    }

}
