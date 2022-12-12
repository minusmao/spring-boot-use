package com.example.spring.boot.use.redis.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 人员管理-班级
 * </p>
 *
 * @author minus
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PmsClass对象", description = "人员管理-班级")
public class PmsClass extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级编号")
    private String number;

    @ApiModelProperty(value = "年级（如2016级、2017级）")
    private String grade;

    @ApiModelProperty(value = "版本")
    private Integer version;

}
