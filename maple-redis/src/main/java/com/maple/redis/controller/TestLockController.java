package com.maple.redis.controller;

import com.maple.redis.service.TestLockService;
import com.maple.redis.util.RedisUtil;
import com.maple.redis.util.RedissonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author zhangfuzeng
 * @date 2025/1/9
 */
@Slf4j
@RestController
@RequestMapping("/lock")
@AllArgsConstructor
public class TestLockController {

    private final TestLockService testLockService;

    private final RedissonUtil redisUtil;

    @GetMapping("/handleStock")
    public void handleStock() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                5,
                10,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        Random random = new Random();
        redisUtil.set("maple:stock", 100);
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            threadPool.execute(() -> testLockService.handleStock(finalI, random.nextInt(10) + 1, 50L));
        }
    }

    @GetMapping("/handleStockSynchronized")
    public void handleStockSynchronized() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                5,
                10,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        Random random = new Random();
        redisUtil.set("maple:stock", 100);
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            threadPool.execute(() -> testLockService.handleStockSynchronized(finalI, random.nextInt(10) + 1, 50L));
        }
    }

    @GetMapping("/handleStockRedisLock")
    public void handleStockRedisLock() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                5,
                10,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        Random random = new Random();
        redisUtil.set("maple:stock", 100);
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            threadPool.execute(() -> testLockService.handleStockRedisLock(finalI, random.nextInt(10) + 1, 60L));
        }
    }

    @GetMapping("/handleStockRedisLockAnnotation")
    public void handleStockRedisLockAnnotation() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                5,
                10,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        Random random = new Random();
        redisUtil.set("maple:stock", 100);
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            threadPool.execute(() -> testLockService.handleStockRedisLockAnnotation(finalI, random.nextInt(10) + 1, 600L));
        }
    }

}
