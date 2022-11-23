package com.example.spring.boot.use.file.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件信息VO
 *
 * @author minus
 * @since 2022/11/23 22:06
 */
@Data
@ApiModel("统一响应结果")
public class FileInfoVO {

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件类型")
    private String contentType;

    @ApiModelProperty("文件大小（单位：B）")
    private Long fileSize;

}
