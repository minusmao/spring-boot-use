package com.example.spring.boot.use.rabbitmq.controller;

import com.example.spring.boot.use.rabbitmq.common.ApiConst;
import com.example.spring.boot.use.rabbitmq.model.ResultVO;
import com.example.spring.boot.use.rabbitmq.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/17 23:10
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/test")
@Api(tags = "测试")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/mail")
    @ApiOperation(value = "API-01-测试（消息队列发送邮件模拟）")
    public ResultVO<Object> testSendMail(
            @ApiParam("发送者") @RequestParam String sender,
            @ApiParam("接收者") @RequestParam String receiver,
            @ApiParam("邮件内容") @RequestParam String content
    ) {
        testService.testSendMail(sender, receiver, content);
        return ResultVO.suc();
    }

    @PostMapping("/msg")
    @ApiOperation(value = "API-01-测试（消息队列发送短信模拟，且延时1分钟）")
    public ResultVO<Object> testSendMsgDelay(
            @ApiParam("发送者") @RequestParam String sender,
            @ApiParam("接收者") @RequestParam String receiver,
            @ApiParam("短信内容") @RequestParam String content
    ) {
        testService.testSendMsgDelay(sender, receiver, content);
        return ResultVO.suc();
    }

}
