package com.example.spring.boot.use.async.service.impl;

import com.example.spring.boot.use.async.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 测试@Async注解
 *
 * @author minus
 * @since 2022/12/1 20:39
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    private final Logger log = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Async
    @Override
    public void testAsync1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testAsync1");
    }

    @Async("threadPoolExecutor")
    @Override
    public void testAsync2() {
        log.info("testAsync2");
    }

}
