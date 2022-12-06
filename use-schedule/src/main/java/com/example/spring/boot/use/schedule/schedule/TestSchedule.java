package com.example.spring.boot.use.schedule.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 测试@Schedule定时任务
 *
 * @author minus
 * @since 2022/12/4 22:32
 */
@Component
@Slf4j
public class TestSchedule {

    /**
     * 定时任务（通过cron表达式指定）
     * 在线cron表达式生成：http://cron.ciding.cc/
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduleCron() {
        log.info("定时任务scheduleCron()执行，当前时间：" + LocalDateTime.now());
    }

    /**
     * 定时任务（通过fixedDelay指定，单位毫秒）
     * fixedDelay -> 上一次任务完成与下一次任务开始的时间间隔
     */
    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelay() {
        log.info("定时任务scheduleFixedDelay()执行，当前时间：" + LocalDateTime.now());
        // 延时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时任务（通过fixedRate指定，单位毫秒）
     * fixedRate -> 上一次任务开始和下一次任务开始的时间间隔（ThreadPoolTaskScheduler的线程不够时，任务会排队阻塞）
     */
    @Scheduled(fixedRate = 5000)
    public void scheduleFixedRate() {
        log.info("定时任务scheduleFixedRate()执行，当前时间：" + LocalDateTime.now());
        // 延时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时任务（通过cron表达式指定）
     * 在线cron表达式生成：http://cron.ciding.cc/
     * 使用@Async的线程池执行
     */
    @Scheduled(cron = "0/5 * * * * ?")
    @Async
    public void scheduleCronAsync() {
        log.info("定时任务scheduleCronAsync()执行，当前时间：" + LocalDateTime.now());
    }

}
