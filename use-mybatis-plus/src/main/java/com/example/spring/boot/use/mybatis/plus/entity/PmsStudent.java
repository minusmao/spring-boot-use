package com.example.spring.boot.use.mybatis.plus.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class PmsStudent extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学号")
    private String number;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别（0-女 1-男）")
    private String sex;

    @ApiModelProperty(value = "生日")
    // IGNORED-忽略判断直接更新、NOT_NULL-不为null才更新、NOT_EMPTY-不为empty才更新、DEFAULT跟随全局（全局设置default则为NOT_NULL）、NEVER不参与SQL语句
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDate birth;

    @ApiModelProperty(value = "电话")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String phone;

    @ApiModelProperty(value = "班级编号")
    private String classNumber;

}
