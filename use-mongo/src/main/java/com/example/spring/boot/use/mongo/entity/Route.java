package com.example.spring.boot.use.mongo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 道路信息
 *
 * @author minus
 * @since 2022/12/20 20:03
 */
@Data
@ApiModel(value = "Route对象", description = "道路信息")
@Document("route")
public class Route {

    @ApiModelProperty("主键id")
    @Id
    private String id;

    @ApiModelProperty("道路编号")
    private String routeNumber;

    @ApiModelProperty("道路名称")
    private String routeName;

    @ApiModelProperty("行政区划代码")
    private String areaCode;

    @ApiModelProperty("行政区划名称（测试忽略字段）")
    @Transient
    private String areaName;

    @ApiModelProperty("长度（单位：米）")
    private Double length;

    @ApiModelProperty("路线")
    private String line;

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
