package com.example.spring.boot.use.schedule.schedule;

/**
 * 定时任务管理-管理器接口
 *
 * @author minus
 * @since 2022/12/6 22:02
 */
public interface ScheduledTaskManager {

    /**
     * 开启定时任务
     *
     * @param taskKey 任务键（例：com.example.spring.boot.use.schedule.schedule.task.ScheduledTaskJob1）
     * @param cron    cron表达式
     * @return 是否成功
     */
    boolean start(String taskKey, String cron);

    /**
     * 停止定时任务
     *
     * @param taskKey 任务键
     * @return 是否成功
     */
    boolean stop(String taskKey);

    /**
     * 重启定时任务
     *
     * @param taskKey 任务键
     * @param cron    cron表达式
     */
    boolean restart(String taskKey, String cron);

    /**
     * 加载启动已经开启的定时任务（用于项目启动后，加载启动已开启的定时任务）
     */
    void loadStartedTasks();

}
