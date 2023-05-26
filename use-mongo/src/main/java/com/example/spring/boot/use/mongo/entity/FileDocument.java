package com.example.spring.boot.use.mongo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 文件存储（大于16MB）
 *
 * @author minus
 * @since 2023/05/24 20:57
 */
@Data
@ApiModel(value = "FileDocument对象", description = "文件存储（大于16MB）")
@Document("file_document")
public class FileDocument {

    @ApiModelProperty("主键id")
    @Id
    private String id;

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件类型")
    private String contentType;

    @ApiModelProperty("文件大小（单位：B）")
    private long size;

    @ApiModelProperty("上传时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty("文件MD5摘要")
    private String md5;

    @ApiModelProperty("文件GridFS的id")
    private String gridFSId;

    @ApiModelProperty("创建人")
    @CreatedBy
    private String createPerson;

    @ApiModelProperty("创建时间")
    @CreatedDate
    private LocalDateTime createTime;

    @ApiModelProperty("更新人")
    @LastModifiedBy
    private String updatePerson;

    @ApiModelProperty("更新时间")
    @LastModifiedDate
    private LocalDateTime updateTime;

}
