package com.maple.redis.controller;

import com.maple.redis.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangfuzeng
 * @date 2025/1/7
 */
@Slf4j
@RestController
@RequestMapping("/cache")
public class TestCacheController {

    @GetMapping("/simpleCache")
    @Cacheable(cacheNames = "cache:simpleCache")
    public String simpleCache() throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了simpleCache方法");
        return "test";
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames = "cache:cacheByKey", key = "#id")
    public Integer cacheByKey(@PathVariable("id") Integer id) throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了cacheByKey方法" + id);
        return id;
    }

    @PostMapping("/cacheByUser")
    @Cacheable(cacheNames = "cache2:cacheByUser", key = "#user.id")
    public User cacheByUser(@RequestBody User user) throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了cacheByUser方法" + user.getId());
        return user;
    }

    @PostMapping("/cacheByIdAndName")
    @Cacheable(cacheNames = "cache2:cacheByUser", key = "#user.id")
    public User cacheByIdAndName(@RequestBody User user) throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了cacheByUser方法" + user.getId());
        return user;
    }

    /**
     * 根据用户ID大于100的条件进行缓存处理。
     *
     * @param user 用户对象，包含用户ID等信息。
     * @return 返回传入的用户对象。
     * @throws InterruptedException 如果线程被中断，则抛出此异常。
     *
     * <p>该方法使用@PostMapping注解定义了一个处理HTTP POST请求的映射路径"/cacheByUserIdGt100"。
     * 通过@Cacheable注解实现了缓存功能，当请求的用户ID大于100时，会触发缓存机制。
     * 缓存的名称设置为"cache2:cacheByUser"，缓存的键为传入的用户对象的ID。
     * 如果缓存中已存在对应的用户数据，则直接从缓存中获取并返回，避免重复执行耗时操作。
     * 如果缓存中不存在，则执行方法体中的逻辑，将结果存入缓存并返回。
     * 在方法执行过程中，通过Thread.sleep模拟了一个耗时操作。
     */
    @PostMapping("/cacheByUserIdGt100")
    @Cacheable(cacheNames = "cache2:cacheByUser", key = "#user.id", condition = "#user.id > 100")
    public User cacheByUserIdGt100(@RequestBody User user) throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了cacheByUser方法" + user.getId());
        return user;
    }

    @PostMapping("/updateUser")
    @CachePut(cacheNames = "cache2:cacheByUser", key = "#user.id")
    public User saveUser(@RequestBody User user) throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了saveUser方法" + user.getId());
        return user;
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "cache2:cacheByUser", key = "#id")
    public void deleteUser(@PathVariable("id") Integer id) throws InterruptedException {
        Thread.sleep(10000);
        log.info("执行了deleteUser方法" + id);
    }
}
