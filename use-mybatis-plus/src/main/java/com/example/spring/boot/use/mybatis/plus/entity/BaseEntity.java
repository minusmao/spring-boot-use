package com.example.spring.boot.use.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.*;
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

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("创建人")
    private String createPerson;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新人")
    private String updatePerson;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @TableLogic
    @ApiModelProperty("是否删除")
    private Boolean deleted;

}
