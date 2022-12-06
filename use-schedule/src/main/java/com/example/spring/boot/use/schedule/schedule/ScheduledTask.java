package com.example.spring.boot.use.schedule.schedule;

/**
 * 定时任务管理-定时任务接口
 *
 * @author minus
 * @since 2022/12/4 23:57
 */
public interface ScheduledTask extends Runnable {

    @Override
    default void run() {
        execute();
    }

    /**
     * 执行任务
     */
    void execute();

}
