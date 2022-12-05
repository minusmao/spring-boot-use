package com.example.spring.boot.use.valid.entity;

import com.example.spring.boot.use.valid.common.valid.group.GroupSave;
import com.example.spring.boot.use.valid.common.valid.group.GroupUpdate;
import com.example.spring.boot.use.valid.common.valid.validator.IdCardNumber;
import com.example.spring.boot.use.valid.common.valid.validator.Phone;
import com.example.spring.boot.use.valid.common.valid.validator.TimeInterval;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 人员管理-人员
 * </p>
 *
 * @author minus
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PmsPerson对象", description = "人员管理-人员")
@TimeInterval(groups = {GroupSave.class, GroupUpdate.class},
        startFiled = "idCardPeriodStart", endFiled = "idCardPeriodEnd", message = "身份证有效期起需要小于身份证有效期止")
public class PmsPerson extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "身份证号")
    @IdCardNumber(groups = {GroupSave.class, GroupUpdate.class})
    private String idCardNumber;

    @ApiModelProperty(value = "姓名")
    @NotNull(groups = {GroupSave.class}, message = "姓名不能为空")
    @Length(groups = {GroupSave.class, GroupUpdate.class}, min = 1, max = 20, message = "姓名长度需要在1和20之间")
    private String name;

    @ApiModelProperty(value = "性别（0-女 1-男）")
    @Max(groups = {GroupSave.class, GroupUpdate.class}, value = 1, message = "性别不在可选范围")
    @Min(groups = {GroupSave.class, GroupUpdate.class}, value = 0, message = "性别不在可选范围")
    private Integer sex;

    @ApiModelProperty(value = "身高")
    @DecimalMin(groups = {GroupSave.class, GroupUpdate.class}, value = "0", message = "身高需要在0~300厘米之间")
    @DecimalMax(groups = {GroupSave.class, GroupUpdate.class}, value = "300", message = "身高需要在0~300厘米之间")
    private BigDecimal height;

    @ApiModelProperty(value = "生日")
    @PastOrPresent(groups = {GroupSave.class, GroupUpdate.class}, message = "生日必须为过去或当前日期")
    private LocalDate birth;

    @ApiModelProperty(value = "电话")
    @Phone(groups = {GroupSave.class, GroupUpdate.class})
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    @Email(groups = {GroupSave.class, GroupUpdate.class}, message = "电子邮箱格式错误")
    private String email;

    @ApiModelProperty(value = "身份证有效期起")
    @PastOrPresent(groups = {GroupSave.class, GroupUpdate.class}, message = "身份证有效期起 必须为过去或当前日期")
    private LocalDate idCardPeriodStart;

    @ApiModelProperty(value = "身份证有效期止")
    private LocalDate idCardPeriodEnd;

}
