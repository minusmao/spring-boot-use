package com.example.spring.boot.use.rabbitmq.controller;

import com.example.spring.boot.use.rabbitmq.common.ApiConst;
import com.example.spring.boot.use.rabbitmq.service.BlrTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试编程不良人 rabbitmq 教程
 * <a href="https://www.bilibili.com/video/BV1dE411K7MG/">教程地址</a>
 *
 * @author minus
 * @since 2020/12/6 20:51
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/blr/test")
@Api(tags = "测试-编程不良人")
public class BlrTestController {

    @Autowired
    private BlrTestService blrTestService;

    @PostMapping("/model1")
    @ApiOperation("模型一（Hello world，直连）")
    public void testModel1(@RequestParam(defaultValue = "aaa") String msg) {
        blrTestService.testModel1(msg);
    }

    @PostMapping("/model2")
    @ApiOperation("模型二（work queues）")
    public void testModel2(@RequestParam(defaultValue = "aaa") String msg) {
        blrTestService.testModel2(msg);
    }

    @PostMapping("/model3")
    @ApiOperation("模型三（fanout，即广播）")
    public void testModel3(@RequestParam(defaultValue = "aaa") String msg) {
        blrTestService.testModel3(msg);
    }

    @PostMapping("/model4")
    @ApiOperation("模型四（Routing-Direct，直连）")
    public void testModel4(
            @RequestParam(defaultValue = "aaa") String msg,
            @RequestParam(defaultValue = "info") String routingKey
    ) {
        blrTestService.testModel4(msg, routingKey);
    }

    @PostMapping("/model5")
    @ApiOperation("模型五（Routing-Topics，订阅）")
    public void testModel5(
            @RequestParam(defaultValue = "aaa") String msg,
            @RequestParam(defaultValue = "info") String routingKey
    ) {
        blrTestService.testModel5(msg, routingKey);
    }

}
