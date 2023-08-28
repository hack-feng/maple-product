package com.maple.redis.controller;

import com.maple.redis.util.RedissonUtil;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RTransaction;
import org.redisson.transaction.TransactionException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangfuzeng
 * @date 2023/8/28
 */
@RestController
@RequestMapping("/redisson")
public class TestRedissonController {

    private final RedissonUtil redissonUtil;

    public TestRedissonController(RedissonUtil redissonUtil) {
        this.redissonUtil = redissonUtil;
    }

    /**
     * 插入String类型的数据到redis
     */
    @PutMapping("/insertStr")
    public void insertStr(String key, String value) {
        redissonUtil.set(key, value);
    }

    /**
     * 根据key获取redis的数据
     */
    @PostMapping("/getStr")
    public String getStr(String key) {
        return redissonUtil.get(key);
    }

    /**
     * 插入map
     */
    @PostMapping("/insertMap")
    public void insertMap(String key) {
        RMap<String, String> rMap = redissonUtil.getMap(key);
        rMap.put("key1", "xiaoxiaofeng");
        rMap.put("key2", "xiaoxiaofeng2");
        rMap.put("key3", "xiaoxiaofeng3");
    }

    /**
     * 获取map
     */
    @PostMapping("/getMap")
    public Map<String, String> getMap(String key) {
        RMap<String, String> rMap = redissonUtil.getMap(key);
        Set<String> set = new HashSet<>();
        set.add("key1");
        set.add("key3");
        return rMap.getAll(set);
    }

    /**
     * 分布式锁伪代码
     */
    @PostMapping("/getLock")
    public void getLock(String key) {
        RLock lock = redissonUtil.getLock(key);
        try {

            // 尝试加锁，最多等待5秒，上锁以后10秒自动解锁
            boolean isOk = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!isOk) {
                // 获取分布式锁失败，可以进行抛出获取锁失败的提示等，根据业务来
                System.out.println("获取分布式锁失败");
                return;
            }
            // 获取锁成功后，开始处理业务逻辑
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // 如果10s没有执行完，锁会自动释放，这里抛出异常
            Thread.currentThread().interrupt();
        } finally {
            // 如果10s没有执行完，锁会自动释放，这里抛出异常，操作失败，需要对数据回滚
            lock.unlock();
        }
    }

    /**
     * Reids事务演示
     */
    @PostMapping("/getTransaction")
    public void getTransaction() {
        RTransaction transaction = redissonUtil.getTransaction();
        RMap<String, String> map = transaction.getMap("myMap");
        map.put("1", "2");
        String value = map.get("3");
        RSet<String> set = transaction.getSet("mySet");
        set.add(value);
        try {
            transaction.commit();
        } catch (TransactionException e) {
            transaction.rollback();
        }
    }

}
