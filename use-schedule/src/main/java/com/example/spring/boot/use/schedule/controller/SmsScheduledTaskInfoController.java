package com.example.spring.boot.use.schedule.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.schedule.common.ApiConst;
import com.example.spring.boot.use.schedule.entity.SmsScheduledTaskInfo;
import com.example.spring.boot.use.schedule.model.ResultVO;
import com.example.spring.boot.use.schedule.service.SmsScheduledTaskInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统管理-定时任务信息 前端控制器
 * </p>
 *
 * @author minus
 * @since 2022-12-07
 */
@RestController
@RequestMapping(ApiConst.API_URL + "/sms-scheduled-task-info")
@Api(tags = "系统管理-定时任务信息")
public class SmsScheduledTaskInfoController {

    @Autowired
    SmsScheduledTaskInfoService smsScheduledTaskInfoService;

    @PutMapping
    @ApiOperation(value = "API-01-更新定时任务信息")
    public ResultVO<SmsScheduledTaskInfo> updateTaskInfo(@Validated @RequestBody SmsScheduledTaskInfo taskInfo) {
        return ResultVO.suc(smsScheduledTaskInfoService.updateTaskInfo(taskInfo));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API-02-查询定时任务信息")
    public ResultVO<SmsScheduledTaskInfo> getTaskInfoById(
            @ApiParam("主键id") @PathVariable String id
    ) {
        return ResultVO.suc(smsScheduledTaskInfoService.getTaskInfoById(id));
    }

    @GetMapping("/{current}/{size}")
    @ApiOperation(value = "API-03-查询定时任务信息分页")
    public ResultVO<Page<SmsScheduledTaskInfo>> pageClass(
            @ApiParam("页码") @PathVariable Long current,
            @ApiParam("页大小") @PathVariable Long size
    ) {
        return ResultVO.suc(smsScheduledTaskInfoService.pageTaskInfo(new Page<>(current, size)));
    }

}
