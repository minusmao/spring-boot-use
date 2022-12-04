package com.example.spring.boot.use.async.manager;

import java.util.TimerTask;

/**
 * 异步工厂（产生TimerTask任务用）
 *
 * @author minus
 * @since 2022/12/4 16:35
 */
public class AsyncFactory {

    /**
     * 记录登录日志
     *
     * @return 任务task
     */
    public static TimerTask recordLogin() {
        return new TimerTask() {
            @Override
            public void run() {
                // TODO 保存登录日志，将在use-log中补充
            }
        };
    }

    /**
     * 记录操作日志
     *
     * @return 任务task
     */
    public static TimerTask recordOperation() {
        return new TimerTask() {
            @Override
            public void run() {
                // TODO 保存操作日志，将在use-log中补充
            }
        };
    }
}
