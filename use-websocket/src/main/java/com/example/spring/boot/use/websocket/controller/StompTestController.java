package com.example.spring.boot.use.websocket.controller;

import com.example.spring.boot.use.websocket.common.ApiConst;
import com.example.spring.boot.use.websocket.model.StompMassageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 测试STOMP
 *
 * @author minus
 * @since 2024/1/31 20:59
 */
@Slf4j
@RestController
@RequestMapping(ApiConst.API_URL + "/stomp")
@Api(tags = "测试STOMP")
public class StompTestController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @ApiOperation(value = "API-01-广播")
    @PostMapping("/pushToAll")
    public void toAll(@RequestBody StompMassageDTO msg) {
        simpMessagingTemplate.convertAndSend("/topic/all", msg);
    }

    @MessageMapping("/topic/all")    // 用来接受客户端的消息
    public void getMassage(@RequestBody StompMassageDTO msg) {
        log.info("收到消息：" + msg);
    }

    @ApiOperation(value = "API-03-点对点")
    @MessageMapping("/alone")    // 用来接受客户端的消息
    @PostMapping("/pushToUser")
    public void toUser(@RequestBody StompMassageDTO msg) {
        /*
            使用convertAndSendToUser方法，第一个参数为用户id，此时js中的订阅地址为
            "/user/" + 用户Id + "/message"，其中"/user"是固定的
        */
        simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/message", msg);
    }

}
