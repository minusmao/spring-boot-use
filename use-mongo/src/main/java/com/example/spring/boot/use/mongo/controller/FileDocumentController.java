package com.example.spring.boot.use.mongo.controller;

import com.example.spring.boot.use.mongo.common.ApiConst;
import com.example.spring.boot.use.mongo.entity.FileDocument;
import com.example.spring.boot.use.mongo.model.ResultVO;
import com.example.spring.boot.use.mongo.service.FileDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储（大于16MB）
 * 注：MongoDB单个文档的存储限制是16MB，如果要存储大于16MB的文件，就要用到MongoDB GridFS
 *
 * @author minus
 * @since 2023/05/19 20:57
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/file/document")
@Api(tags = "文件存储（大于16MB）")
public class FileDocumentController {

    @Autowired
    private FileDocumentService fileDocumentService;

    @PostMapping
    @ApiOperation(value = "API-01-新增文件")
    public ResultVO<FileDocument> saveFile(
            @ApiParam("文件") @RequestPart MultipartFile file
    ) {
        return ResultVO.suc(fileDocumentService.saveFile(file));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "API-02-删除文件")
    public ResultVO<Object> removeFile(
            @ApiParam("文件id") @PathVariable String id
    ) {
        fileDocumentService.removeFile(id);
        return ResultVO.suc();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API-03-查询文件信息")
    public ResultVO<FileDocument> findFile(
            @ApiParam("文件id") @PathVariable String id
    ) {
        return ResultVO.suc(fileDocumentService.findFile(id));
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "API-04-下载文件")
    public ResponseEntity<GridFsResource> downloadFile(
            @ApiParam("文件id") @PathVariable String id
    ) {
        return fileDocumentService.downloadFile(id);
    }

}
