package com.example.spring.boot.use.async.manager;

import com.example.spring.boot.use.async.util.SpringUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步管理器
 * 参考：若依框架
 *
 * @author minus
 * @since 2022/12/4 16:35
 */
public class AsyncManager {

    /**
     * 操作延迟10毫秒
     */
    private static final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private final ScheduledExecutorService scheduledExecutor;

    /**
     * 单例模式
     */
    private AsyncManager() {
        // 拿到系统的ThreadPoolTaskScheduler实例
        this.scheduledExecutor = SpringUtil.getBean(ThreadPoolTaskScheduler.class).getScheduledExecutor();
    }

    private static final AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        return me;
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        scheduledExecutor.schedule(task, AsyncManager.OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

}
