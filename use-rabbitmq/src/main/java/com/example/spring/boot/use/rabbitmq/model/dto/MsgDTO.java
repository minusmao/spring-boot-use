package com.example.spring.boot.use.rabbitmq.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信DTO
 *
 * @author minus
 * @since 2022/12/18 2:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("短信DTO")
public class MsgDTO implements Serializable {

    @ApiModelProperty("发送者")
    private String sender;

    @ApiModelProperty("接收者")
    private String receiver;

    @ApiModelProperty("邮件内容")
    private String content;

    @ApiModelProperty("时间")
    private LocalDateTime time;

}
