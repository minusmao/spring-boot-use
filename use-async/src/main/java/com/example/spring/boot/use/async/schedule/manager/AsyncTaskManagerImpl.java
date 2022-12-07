package com.example.spring.boot.use.async.schedule.manager;

import com.example.spring.boot.use.async.schedule.AsyncTaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理-管理器
 * 参考若依框架：https://ruoyi.vip/
 *
 * @author minus
 * @since 2022/12/4 16:35
 */
@Component
public class AsyncTaskManagerImpl implements AsyncTaskManager {

    /**
     * 定时任务调度池
     */
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public void execute(TimerTask task) {
        // 默认增加了10毫秒的延时
        executeDelay(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduledFuture<?> executeDelay(TimerTask task, long delay, TimeUnit unit) {
        return threadPoolTaskScheduler.getScheduledThreadPoolExecutor().schedule(task, delay, unit);
    }

    @Override
    public ScheduledFuture<?> executeDelayMillisecond(TimerTask task, long milliseconds) {
        return executeDelay(task, milliseconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduledFuture<?> executeDelaySecond(TimerTask task, long seconds) {
        return executeDelay(task, seconds, TimeUnit.SECONDS);
    }

    @Override
    public ScheduledFuture<?> executeDelayMinutes(TimerTask task, long minutes) {
        return executeDelay(task, minutes, TimeUnit.MINUTES);
    }

    @Override
    public ScheduledFuture<?> executeDelayHours(TimerTask task, long hours) {
        return executeDelay(task, hours, TimeUnit.HOURS);
    }

    @Override
    public ScheduledFuture<?> execute(TimerTask task, LocalDateTime dateTime) {
        return threadPoolTaskScheduler.schedule(task, dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public boolean cancelTaskFuture(ScheduledFuture<?> taskFuture) {
        return taskFuture.cancel(true);
    }

}
