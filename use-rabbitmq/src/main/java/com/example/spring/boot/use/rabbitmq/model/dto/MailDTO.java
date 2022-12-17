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
 * 邮件DTO
 *
 * @author minus
 * @since 2022/12/17 23:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("邮件DTO")
public class MailDTO implements Serializable {

    @ApiModelProperty("发送者")
    private String sender;

    @ApiModelProperty("接收者")
    private String receiver;

    @ApiModelProperty("邮件内容")
    private String content;

    @ApiModelProperty("时间")
    private LocalDateTime time;

}
