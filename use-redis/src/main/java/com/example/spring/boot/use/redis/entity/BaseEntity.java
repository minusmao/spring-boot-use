package com.example.spring.boot.use.redis.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * entity层的公共类
 *
 * @author minus
 * @since 2022/11/21 1:32
 */
@Data
@EqualsAndHashCode
public class BaseEntity {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("创建人")
    private String createPerson;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新人")
    private String updatePerson;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

}
