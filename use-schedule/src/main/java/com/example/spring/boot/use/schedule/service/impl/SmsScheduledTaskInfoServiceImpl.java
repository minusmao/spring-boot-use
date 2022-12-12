package com.example.spring.boot.use.schedule.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.boot.use.schedule.entity.SmsScheduledTaskInfo;
import com.example.spring.boot.use.schedule.mapper.SmsScheduledTaskInfoMapper;
import com.example.spring.boot.use.schedule.schedule.ScheduledTaskManager;
import com.example.spring.boot.use.schedule.service.SmsScheduledTaskInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统管理-定时任务信息 服务实现类
 * </p>
 *
 * @author minus
 * @since 2022-12-07
 */
@Service
public class SmsScheduledTaskInfoServiceImpl extends ServiceImpl<SmsScheduledTaskInfoMapper, SmsScheduledTaskInfo> implements SmsScheduledTaskInfoService {

    @Lazy
    @Autowired
    private ScheduledTaskManager scheduledTaskManager;

    @Transactional
    @Override
    public SmsScheduledTaskInfo updateTaskInfo(SmsScheduledTaskInfo taskInfo) {
        // 校验cron表达式是否合法
        if (!CronExpression.isValidExpression(taskInfo.getCron())) {
            throw new IllegalArgumentException("任务键" + taskInfo.getTaskKey() + "指定cron表达式不合法：" + taskInfo.getCron());
        }
        // 更新任务
        updateById(taskInfo);
        SmsScheduledTaskInfo newTaskInfo = getById(taskInfo.getId());
        if (taskInfo.getStatus() == 0) {
            scheduledTaskManager.stop(newTaskInfo.getTaskKey());
        } else if (taskInfo.getStatus() == 1){
            scheduledTaskManager.start(newTaskInfo.getTaskKey(), taskInfo.getCron());
        }
        return taskInfo;
    }

    @Override
    public SmsScheduledTaskInfo getTaskInfoById(String id) {
        return getById(id);
    }

    @Override
    public Page<SmsScheduledTaskInfo> pageTaskInfo(Page<SmsScheduledTaskInfo> page) {
        return page(page);
    }

}
