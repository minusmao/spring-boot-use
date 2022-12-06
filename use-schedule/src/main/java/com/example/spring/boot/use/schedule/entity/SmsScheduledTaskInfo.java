package com.example.spring.boot.use.schedule.entity;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * <p>
 * 系统管理-定时任务信息
 * </p>
 *
 * @author minus
 * @since 2022-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmsScheduledTaskInfo对象", description="系统管理-定时任务信息")
public class SmsScheduledTaskInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.NONE)
    @NotEmpty(message = "主键不能为空")
    private String id;

    @ApiModelProperty(value = "任务键")
    @Null(message = "任务键不能修改，请去掉taskKey字段")
    private String taskKey;

    @ApiModelProperty(value = "cron表达式")
    @NotEmpty(message = "cron表达式不能为空")
    private String cron;

    @ApiModelProperty(value = "描述")
    @Length(max = 50, message = "描述不能超过50个字符")
    private String description;

    @ApiModelProperty(value = "状态（0-停止 1-启动）")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "更新人")
    private String updatePerson;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
