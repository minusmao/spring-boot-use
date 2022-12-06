package com.example.spring.boot.use.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.schedule.entity.SmsScheduledTaskInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring.boot.use.schedule.model.ResultVO;

/**
 * <p>
 * 系统管理-定时任务信息 服务类
 * </p>
 *
 * @author minus
 * @since 2022-12-07
 */
public interface SmsScheduledTaskInfoService extends IService<SmsScheduledTaskInfo> {

    ResultVO<Object> updateTaskInfo(SmsScheduledTaskInfo taskInfo);

    ResultVO<SmsScheduledTaskInfo> getTaskInfoById(String id);

    ResultVO<Page<SmsScheduledTaskInfo>> pageTaskInfo(Page<SmsScheduledTaskInfo> page);

}
