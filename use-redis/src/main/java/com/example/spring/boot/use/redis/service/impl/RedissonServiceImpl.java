package com.example.spring.boot.use.redis.service.impl;

import com.example.spring.boot.use.redis.model.ResultVO;
import com.example.spring.boot.use.redis.service.RedissonService;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Redisson框架使用
 *
 * @author minus
 * @since 2022/12/12 1:29
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 读写锁key
    private final String READE_WRITE_LOCK_KEY = "READE_WRITE_LOCK";

    // 信号量key（需提前设置到redis中）
    private final String SEMAPHORE_KEY = "SEMAPHORE";

    // 闭锁key
    private final String COUNT_DOWN_KEY = "COUNT_DOWN";

    private String value = "1000";

    @PostConstruct
    public void init() {
        // 设置信号量到redis
        redisTemplate.opsForValue().set("SEMAPHORE", 3);
    }

    @Override
    public ResultVO<Object> doWrite(String newValue) {
        final RReadWriteLock rReadWriteLock = redisson.getReadWriteLock(READE_WRITE_LOCK_KEY);
        final RLock lock = rReadWriteLock.writeLock();
        try {
            // 加锁
            lock.lock();
            // 执行业务代码：写值
            value = newValue;
            Thread.sleep(10000);   // 模拟写了10秒
            return ResultVO.suc();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResultVO.fail(500, "发生未知错误");
        } finally {
            // 解锁
            lock.unlock();
        }
    }

    @Override
    public ResultVO<String> doRead() {
        final RReadWriteLock rReadWriteLock = redisson.getReadWriteLock(READE_WRITE_LOCK_KEY);
        final RLock lock = rReadWriteLock.readLock();
        try {
            // 加锁
            lock.lock();
            // 执行业务代码：读值
            Thread.sleep(1000);    // 模拟读了1秒
            return ResultVO.suc(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResultVO.fail(500, "发生未知错误");
        } finally {
            // 解锁
            lock.unlock();
        }
    }

    @Override
    public ResultVO<Integer> doAcquire() {
        final RSemaphore semaphore = redisson.getSemaphore(SEMAPHORE_KEY);
        try {
            semaphore.acquire();    // 信号量减1
            return ResultVO.suc((Integer) redisTemplate.opsForValue().get(SEMAPHORE_KEY));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResultVO.fail(500, "发生未知错误");
        }
    }

    @Override
    public ResultVO<Integer> doRelease() {
        final RSemaphore semaphore = redisson.getSemaphore(SEMAPHORE_KEY);
        semaphore.release();    // 信号量加1
        return ResultVO.suc((Integer) redisTemplate.opsForValue().get(SEMAPHORE_KEY));
    }

    @Override
    public ResultVO<Integer> doAwait() {
        try {
            final RCountDownLatch countDownLatch = redisson.getCountDownLatch(COUNT_DOWN_KEY);
            // 设置计数总数
            countDownLatch.trySetCount(5L);
            // 等待
            countDownLatch.await();

            return ResultVO.suc((Integer) redisTemplate.opsForValue().get(COUNT_DOWN_KEY));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResultVO.fail(500, "发生未知错误");
        }
    }

    @Override
    public ResultVO<Integer> doCountDown() {
        final RCountDownLatch countDownLatch = redisson.getCountDownLatch(COUNT_DOWN_KEY);
        // 计数减1
        countDownLatch.countDown();

        return ResultVO.suc((Integer) redisTemplate.opsForValue().get(COUNT_DOWN_KEY));
    }

}
