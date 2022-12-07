package com.example.spring.boot.use.async.service.impl;

import com.example.spring.boot.use.async.schedule.AsyncTaskFactory;
import com.example.spring.boot.use.async.schedule.AsyncTaskManager;
import com.example.spring.boot.use.async.service.AsyncService;
import com.example.spring.boot.use.async.service.CompletableFutureService;
import com.example.spring.boot.use.async.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/1 0:36
 */
@Service
public class TestServiceImpl implements TestService {

    private final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private CompletableFutureService completableFutureService;

    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public void testAsync() {
        log.info("testAsync");
        asyncService.testAsync1();
        asyncService.testAsync2();
    }

    @Override
    public void testCompletableFuture() {
        completableFutureService.testCompletableFuture();
    }

    @Override
    public void testAsyncTaskManager() {
        asyncTaskManager.execute(AsyncTaskFactory.recordLogin());
        ScheduledFuture<?> taskFuture = asyncTaskManager.executeDelaySecond(AsyncTaskFactory.recordOperation(), 10);
        // 测试取消异步任务
        if(asyncTaskManager.cancelTaskFuture(taskFuture)) {
            log.info("取消异步任务成功");
            asyncTaskManager.executeDelaySecond(AsyncTaskFactory.recordOperation(), 5);
        }
    }

}
