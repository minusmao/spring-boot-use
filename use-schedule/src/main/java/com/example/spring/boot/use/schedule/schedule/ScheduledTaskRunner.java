package com.example.spring.boot.use.schedule.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动时，初始化定时任务
 *
 * @author minus
 * @since 2022/12/6 21:37
 */
@Component
@Slf4j
public class ScheduledTaskRunner implements ApplicationRunner {

    @Autowired
    private ScheduledTaskManager scheduledTaskManager;

    @Override
    public void run(ApplicationArguments args) {
        log.info("启动已开启的定时任务：开始");
        scheduledTaskManager.loadStartedTasks();
        log.info("启动已开启的定时任务：结束");
    }
}
