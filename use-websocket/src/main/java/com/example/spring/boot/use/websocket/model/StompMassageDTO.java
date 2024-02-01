package com.example.spring.boot.use.websocket.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 推送消息的实体类
 *
 * @author minus
 * @since 2024/1/31 16:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Stomp消息")
public class StompMassageDTO {

    @ApiModelProperty("消息编码")
    private String code;

    @ApiModelProperty("来自（保证唯一）")
    private String form;

    @ApiModelProperty("去自（保证唯一）")
    private String to;

    @ApiModelProperty("内容")
    private String content;

}
