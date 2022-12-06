package com.example.spring.boot.use.schedule.schedule.manager;

import com.example.spring.boot.use.schedule.entity.SmsScheduledTaskInfo;
import com.example.spring.boot.use.schedule.schedule.ScheduledTask;
import com.example.spring.boot.use.schedule.schedule.ScheduledTaskManager;
import com.example.spring.boot.use.schedule.service.SmsScheduledTaskInfoService;
import com.example.spring.boot.use.schedule.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务管理-管理器
 *
 * @author minus
 * @since 2022/12/6 22:22
 */
@Slf4j
@Component
public class ScheduledTaskManagerImpl implements ScheduledTaskManager {

    @Autowired
    private SmsScheduledTaskInfoService taskInfoService;

    /**
     * 定时任务调度池
     */
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 存放正在运行的定时任务Future容器
     */
    public Map<String, ScheduledFuture<?>> scheduledTaskFutureMap = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean start(String taskKey, String cron) {
        if (isStarted(taskKey)) {
            log.info("任务键" + taskKey + "已启动，无需再次启动");
            return false;
        } else {
            doStartTask(taskKey, cron);
            log.info("任务键" + taskKey + "启动成功");
            return true;
        }
    }

    @Override
    public boolean stop(String taskKey) {
        if (scheduledTaskFutureMap.containsKey(taskKey)) {
            ScheduledFuture<?> scheduledFuture = scheduledTaskFutureMap.get(taskKey);
            scheduledFuture.cancel(true);
            scheduledTaskFutureMap.remove(taskKey);
            log.info("任务键" + taskKey + "停止成功");
            return true;
        } else {
            log.info("任务键" + taskKey + "不存在于任务池，无需停止");
            return false;
        }
    }

    @Override
    public boolean restart(String taskKey, String cron) {
        log.info("重启任务键" + taskKey);
        stop(taskKey);
        return start(taskKey, cron);
    }

    @Override
    public void loadStartedTasks() {
        // 查询数据库中所有的定时任务
        List<SmsScheduledTaskInfo> taskInfoList = taskInfoService.list();
        for (SmsScheduledTaskInfo taskInfo : taskInfoList) {
            if (taskInfo.getStatus() == 1) {
                start(taskInfo.getTaskKey(), taskInfo.getCron());
            }
        }
    }

    /**
     * 查看指定taskKey定时任务是否已经启动
     *
     * @param taskKey 任务键
     */
    private boolean isStarted(String taskKey) {
        if (scheduledTaskFutureMap.containsKey(taskKey)) {
            if (!scheduledTaskFutureMap.get(taskKey).isCancelled()) {
                return true;
            } else {
                scheduledTaskFutureMap.remove(taskKey);
            }
        }
        return false;
    }

    /**
     * 根据taskKey启动指定定时任务实现类
     *
     * @param taskKey 任务键
     */
    private void doStartTask(String taskKey, String cron) {
        // 校验cron表达式
        if (!CronExpression.isValidExpression(cron)) {
            throw new IllegalArgumentException("任务键" + taskKey + "指定cron表达式不合法：" + cron);
        }
        // 通过反射拿到任务实现类并校验
        ScheduledTask scheduledTask;
        try {
            Class<?> clazz = Class.forName(taskKey);
            scheduledTask = (ScheduledTask) SpringUtil.getBean(clazz);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("任务键" + taskKey + "指定类不存在", e);
        }
        Assert.isAssignable(ScheduledTask.class, scheduledTask.getClass(), "定时任务类" + taskKey + "必须实现ScheduledTask接口");
        // 将任务实现类放入任务池运行
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler
                .schedule(scheduledTask, (triggerContext -> new CronTrigger(cron).nextExecutionTime(triggerContext)));
        scheduledTaskFutureMap.put(taskKey, scheduledFuture);
    }
}
