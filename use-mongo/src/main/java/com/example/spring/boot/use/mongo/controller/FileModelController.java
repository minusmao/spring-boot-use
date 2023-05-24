package com.example.spring.boot.use.mongo.controller;

import com.example.spring.boot.use.mongo.common.ApiConst;
import com.example.spring.boot.use.mongo.entity.FileModel;
import com.example.spring.boot.use.mongo.model.ResultVO;
import com.example.spring.boot.use.mongo.service.FileModelService;
import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储（小于16MB）
 * 注：MongoDB单个文档的存储限制是16MB，如果要存储大于16MB的文件，就要用到MongoDB GridFS
 *
 * @author minus
 * @since 2023/05/19 20:57
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/file/model")
@Api(tags = "文件存储（小于16MB）")
public class FileModelController {

    @Autowired
    private FileModelService fileModelService;

    @PostMapping
    @ApiOperation(value = "API-01-新增文件")
    public ResultVO<FileModel> saveFile(
            @ApiParam("文件") @RequestPart MultipartFile file
    ) {
        return ResultVO.suc(fileModelService.saveFile(file));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "API-02-删除文件")
    public ResultVO<Object> removeFile(
            @ApiParam("文件id") @PathVariable String id
    ) {
        fileModelService.removeFile(id);
        return ResultVO.suc();
    }

    @PutMapping("/name/{id}")
    @ApiOperation(value = "API-03-更新文件名称")
    public ResultVO<UpdateResult> updateFileName(
            @ApiParam("文件id") @PathVariable String id,
            @ApiParam("文件名称") @RequestParam String name
    ) {
        return ResultVO.suc(fileModelService.updateFileName(id, name));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API-04-查询文件信息")
    public ResultVO<FileModel> findFile(
            @ApiParam("文件id") @PathVariable String id
    ) {
        return ResultVO.suc(fileModelService.findFile(id));
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "API-05-下载文件")
    public ResponseEntity<byte[]> downloadFile(
            @ApiParam("文件id") @PathVariable String id
    ) {
        return fileModelService.downloadFile(id);
    }

}
