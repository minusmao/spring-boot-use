package com.example.spring.boot.use.async.schedule;

import java.time.LocalDateTime;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理-管理器接口
 * 参考若依框架：https://ruoyi.vip/
 *
 * @author minus
 * @since 2022/12/7 20:39
 */
public interface AsyncTaskManager {

    /**
     * 操作延迟10毫秒
     */
    int OPERATE_DELAY_TIME = 10;

    /**
     * 执行异步任务（默认延时OPERATE_DELAY_TIME毫秒执行）
     *
     * @param task 任务
     */
    void execute(TimerTask task);

    /**
     * 执行异步任务（延时执行）
     *
     * @param task  任务
     * @param delay 延时时间
     * @param unit  延时单位
     */
    void executeDelay(TimerTask task, long delay, TimeUnit unit);

    /**
     * 执行异步任务（延时执行：毫秒）
     *
     * @param task         任务
     * @param milliseconds 延时时间（毫秒）
     */
    void executeDelayMillisecond(TimerTask task, long milliseconds);

    /**
     * 执行异步任务（延时执行：秒）
     *
     * @param task    任务
     * @param seconds 延时时间（秒）
     */
    void executeDelaySecond(TimerTask task, long seconds);

    /**
     * 执行异步任务（延时执行：分钟）
     *
     * @param task    任务
     * @param minutes 延时时间（分钟）
     */
    void executeDelayMinutes(TimerTask task, long minutes);

    /**
     * 执行异步任务（延时执行：小时）
     *
     * @param task  任务
     * @param hours 延时时间（小时）
     */
    void executeDelayHours(TimerTask task, long hours);

    /**
     * 执行异步任务（延时执行：指定时间点，如果指定时间点小于当前时间则立即执行）
     *
     * @param task  任务
     * @param dateTime 时间点
     */
    void executeDelayHours(TimerTask task, LocalDateTime dateTime);

}
