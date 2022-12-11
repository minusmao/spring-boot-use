package com.example.spring.boot.use.websocket.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * websocket消息
 *
 * @author minus
 * @since 2022/12/8 20:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("websocket消息")
public class MessageDTO {

    @ApiModelProperty("发送方")
    private String sender;

    @ApiModelProperty("接收方")
    private String receiver;

    @ApiModelProperty("内容")
    private String context;

    @ApiModelProperty("时间")
    private LocalDateTime time;

}
