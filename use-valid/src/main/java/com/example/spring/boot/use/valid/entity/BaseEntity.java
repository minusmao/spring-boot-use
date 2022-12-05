package com.example.spring.boot.use.valid.entity;

import com.example.spring.boot.use.valid.common.valid.group.GroupSave;
import com.example.spring.boot.use.valid.common.valid.group.GroupUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
    @NotNull(groups = GroupUpdate.class, message = "主键id不能为空")
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
    @AssertFalse(message = "逻辑删除字段不能指定为true")
    private Boolean deleted;

}
