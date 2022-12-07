package com.example.spring.boot.use.async.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 异步工厂（产生TimerTask任务）
 * 参考若依框架：https://ruoyi.vip/
 *
 * @author minus
 * @since 2022/12/4 16:35
 */
@Slf4j
public class AsyncTaskFactory {

    /**
     * 记录登录日志
     *
     * @return 任务task
     */
    public static TimerTask recordLogin() {
        return new TimerTask() {
            @Override
            public void run() {
                // TODO 保存登录日志，将在use-log模块中补充
                log.info("记录登录日志......");
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
                // TODO 保存操作日志，将在use-log模块中补充
                log.info("记录操作日志......");
            }
        };
    }
}
