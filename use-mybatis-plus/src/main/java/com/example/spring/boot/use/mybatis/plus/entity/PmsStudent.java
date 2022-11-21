package com.example.spring.boot.use.mybatis.plus.entity;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 人员管理-学生
 * </p>
 *
 * @author minus
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PmsStudent对象", description = "人员管理-学生")
public class PmsStudent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学号")
    private String number;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别（0-女 1-男）")
    private String sex;

    @ApiModelProperty(value = "生日")
    private LocalDate birth;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "班级编号")
    private String classNumber;

}
