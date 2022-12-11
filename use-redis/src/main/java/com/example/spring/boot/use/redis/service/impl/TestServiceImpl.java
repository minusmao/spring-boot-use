package com.example.spring.boot.use.redis.service.impl;

import com.example.spring.boot.use.redis.entity.PmsClass;
import com.example.spring.boot.use.redis.model.ResultVO;
import com.example.spring.boot.use.redis.service.TestService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 测试
 *
 * @author minus
 * @since 2022/12/11 15:18
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 直接操作String类型，自行操作JSON的序列化和反序列化
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redisson;

    // 分布式锁key
    private final String REDIS_LOCK_KEY = "REDIS_LOCK";

    // 模拟数据库
    private final Map<String, PmsClass> pmsClassMap = new HashMap<>();

    @PostConstruct
    public void init() {
        PmsClass pmsClass = new PmsClass();
        pmsClass.setId("201605");
        pmsClass.setNumber("201605");
        pmsClass.setGrade("2016");
        pmsClass.setVersion(1);
        pmsClass.setCreateTime(LocalDateTime.now());
        pmsClassMap.put("201605", pmsClass);
    }

    /**
     * 操作数据库的业务代码（模拟）
     *
     * @param id 主键id
     * @return 结果数据
     */
    private PmsClass getClassByIdInDatabase(String id) {
        return pmsClassMap.get(id);
    }

    @Override
    public ResultVO<PmsClass> getClassById(String id) {
        PmsClass findData;
        // 判断key是否已经缓存
        if (Boolean.valueOf(true).equals(redisTemplate.hasKey(id))) {
            findData = (PmsClass) redisTemplate.opsForValue().get(id);
        } else {
            // 操作数据库的业务代码
            findData = getClassByIdInDatabase(id);
            // 此处为null的数据也缓存了，所以解决了“缓存穿透”的问题
            redisTemplate.opsForValue().set(id, findData, 10, TimeUnit.SECONDS);
        }
        return ResultVO.suc(findData);
    }

    @Override
    public ResultVO<PmsClass> getClassByIdLocalLock(String id) {
        PmsClass findData;
        // 判断key是否已经缓存
        if (Boolean.valueOf(true).equals(redisTemplate.hasKey(id))) {
            findData = (PmsClass) redisTemplate.opsForValue().get(id);
        } else {
            // 查询数据库，通过本地锁解决“缓存击穿”问题
            findData = getWithLocalLock(id);
        }
        return ResultVO.suc(findData);
    }

    /**
     * 通过本地锁解决“缓存击穿”问题
     *
     * @param id 数据key
     * @return 结果
     */
    private PmsClass getWithLocalLock(String id) {
        synchronized (this) {
            PmsClass findData;
            // 再次查看是否已有缓存
            if (Boolean.valueOf(true).equals(redisTemplate.hasKey(id))) {
                findData = (PmsClass) redisTemplate.opsForValue().get(id);
            } else {
                // 操作数据库的业务代码
                findData = getClassByIdInDatabase(id);
                redisTemplate.opsForValue().set(id, findData, 10, TimeUnit.SECONDS);
                return findData;
            }
            return findData;
        }
    }

    @Override
    public ResultVO<PmsClass> getClassByIdRedisLock(String id) {
        PmsClass findData;
        // 判断key是否已经缓存
        if (Boolean.valueOf(true).equals(redisTemplate.hasKey(id))) {
            findData = (PmsClass) redisTemplate.opsForValue().get(id);
        } else {
            // 查询数据库，通过分布式锁解决“缓存击穿”问题
            findData = getWithRedisLock(id);
        }
        return ResultVO.suc(findData);
    }

    /**
     * 通过分布式锁解决“缓存击穿”问题
     * 补充：分布式锁比本地锁更加繁重。当前示例的业务场景采用本地锁也没有问题，使用本地锁，n个服务最多有n个请求走数据库，并不会造成缓存击穿
     *
     * @param id 数据key
     * @return 结果
     */
    private PmsClass getWithRedisLock(String id) {
        // 用于表示锁的唯一性，防止在最后删除锁（解锁）时，其实自身锁已经过期，删掉了其他服务或线程加的锁
        String uuid = UUID.randomUUID().toString();
        // 原子加锁：1、当不存在REDIS_LOCK这个key时，setIfAbsent()方法才会设置成功，即返回true值；2、同时设置了过期时间，防止程序中途崩溃，未执行解锁操作；
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, uuid, 30, TimeUnit.SECONDS);
        if (Boolean.valueOf(true).equals(lock)) {
            PmsClass findData;
            // 再次查看是否已有缓存
            if (Boolean.valueOf(true).equals(redisTemplate.hasKey(id))) {
                findData = (PmsClass) redisTemplate.opsForValue().get(id);
            } else {
                // 操作数据库的业务代码
                findData = getClassByIdInDatabase(id);
                redisTemplate.opsForValue().set(id, findData, 10, TimeUnit.SECONDS);
            }

            // 缺少的逻辑：可能出现业务代码时间大于锁的过期时间的情况
            // 解决的方法：1、加长锁的过期时间；2、在执行业务的同时，进行锁过期时间续期（Redisson框架有做实现）

            // 原子解锁：判断锁是否为自身的锁并删除（两个操作应该为合在一起的原子性操作，利用lua脚本实现）
            String unLockScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            redisTemplate.execute(new DefaultRedisScript<>(unLockScript, Long.class), Collections.singletonList(REDIS_LOCK_KEY), uuid);

            return findData;
        } else {
            // 乐观锁：延时后再次去占锁（自旋）
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getWithLocalLock(id);
        }
    }

    @Override
    public ResultVO<PmsClass> getClassByIdRedissonLock(String id) {
        PmsClass findData;
        // 判断key是否已经缓存
        if (Boolean.valueOf(true).equals(redisTemplate.hasKey(id))) {
            findData = (PmsClass) redisTemplate.opsForValue().get(id);
        } else {
            // 查询数据库，通过分布式锁解决“缓存击穿”问题
            findData = getWithRedissonLock(id);
        }
        return ResultVO.suc(findData);
    }

    /**
     * 通过分布式锁（Redisson框架）解决“缓存击穿”问题
     *
     * @param id 数据key
     * @return 结果
     */
    private PmsClass getWithRedissonLock(String id) {
        final RLock lock = redisson.getLock(REDIS_LOCK_KEY);
        // 加锁：Redisson会自动加上过期时间，默认30秒，由lockWatchdogTimeout属性设置，并且有看门狗进行过期时间续期（续期间隔为过期时间的三分之一）
        lock.lock();    // 注：lock.lock(20, TimeUnit.SECONDS) -> 如果加锁时，指定了过期时间，看门狗会失效
        PmsClass findData;
        // 再次查看是否已有缓存
        if (Boolean.valueOf(true).equals(redisTemplate.hasKey(id))) {
            findData = (PmsClass) redisTemplate.opsForValue().get(id);
        } else {
            // 操作数据库的业务代码
            findData = getClassByIdInDatabase(id);
            redisTemplate.opsForValue().set(id, findData, 10, TimeUnit.SECONDS);
        }
        // 解锁
        lock.unlock();

        return findData;
    }

}
