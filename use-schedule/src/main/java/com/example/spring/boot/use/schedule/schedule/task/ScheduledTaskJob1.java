package com.example.spring.boot.use.schedule.schedule.task;

import com.example.spring.boot.use.schedule.schedule.ScheduledTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务管理-定时任务1
 *
 * @author minus
 * @since 2022/12/6 22:50
 */
@Slf4j
@Component
public class ScheduledTaskJob1 implements ScheduledTask {
    @Override
    public void execute() {
        log.info("执行定时任务1 " + LocalDateTime.now());
    }
}
