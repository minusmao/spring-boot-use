package com.example.spring.boot.use.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.schedule.entity.SmsScheduledTaskInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统管理-定时任务信息 服务类
 * </p>
 *
 * @author minus
 * @since 2022-12-07
 */
public interface SmsScheduledTaskInfoService extends IService<SmsScheduledTaskInfo> {

    SmsScheduledTaskInfo updateTaskInfo(SmsScheduledTaskInfo taskInfo);

    SmsScheduledTaskInfo getTaskInfoById(String id);

    Page<SmsScheduledTaskInfo> pageTaskInfo(Page<SmsScheduledTaskInfo> page);

}
