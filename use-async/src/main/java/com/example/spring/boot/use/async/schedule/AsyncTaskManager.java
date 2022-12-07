package com.example.spring.boot.use.async.schedule;

import java.time.LocalDateTime;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
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
     * @return 任务ScheduledFuture
     */
    ScheduledFuture<?> executeDelay(TimerTask task, long delay, TimeUnit unit);

    /**
     * 执行异步任务（延时执行：毫秒）
     *
     * @param task         任务
     * @param milliseconds 延时时间（毫秒）
     * @return 任务ScheduledFuture
     */
    ScheduledFuture<?> executeDelayMillisecond(TimerTask task, long milliseconds);

    /**
     * 执行异步任务（延时执行：秒）
     *
     * @param task    任务
     * @param seconds 延时时间（秒）
     * @return 任务ScheduledFuture
     */
    ScheduledFuture<?> executeDelaySecond(TimerTask task, long seconds);

    /**
     * 执行异步任务（延时执行：分钟）
     *
     * @param task    任务
     * @param minutes 延时时间（分钟）
     * @return 任务ScheduledFuture
     */
    ScheduledFuture<?> executeDelayMinutes(TimerTask task, long minutes);

    /**
     * 执行异步任务（延时执行：小时）
     *
     * @param task  任务
     * @param hours 延时时间（小时）
     * @return 任务ScheduledFuture
     */
    ScheduledFuture<?> executeDelayHours(TimerTask task, long hours);

    /**
     * 执行异步任务（指定时间点执行，如果指定时间点小于当前时间则立即执行）
     *
     * @param task     任务
     * @param dateTime 时间点
     * @return 任务ScheduledFuture
     */
    ScheduledFuture<?> execute(TimerTask task, LocalDateTime dateTime);

    /**
     * 尝试取消ScheduledFuture任务的执行
     *
     * @param taskFuture 任务taskFuture
     * @return false-取消失败（一般是任务已经完成）、true-取消成功
     */
    boolean cancelTaskFuture(ScheduledFuture<?> taskFuture);

}
