package com.maple.redis.service.impl;

import com.maple.redis.service.TestLockService;
import com.maple.redis.util.RedisLock;
import com.maple.redis.util.RedissonUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangfuzeng
 * @date 2025/1/9
 */
@Slf4j
@Service
@AllArgsConstructor
public class TestLockServiceImpl implements TestLockService {

    private final RedissonUtil redissonUtil;

    @Override
    public void handleStock(Integer index, Integer buyNum, Long sleepTime) {
        try {
            handle(index, buyNum, sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public synchronized void handleStockSynchronized(Integer index, Integer buyNum, Long sleepTime) {
        try {
            handle(index, buyNum, sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleStockRedisLock(Integer index, Integer buyNum, Long sleepTime) {
        RLock lock = redissonUtil.getLock("handleStockRedisLock");
        boolean isOk = false;
        try {
            isOk = lock.tryLock(5, TimeUnit.SECONDS);
            if (!isOk) {
                log.error("第" + index + "次，获取分布式锁失败");
                return;
            }
            handle(index, buyNum, sleepTime);
        } catch (InterruptedException e) {
            log.error("线程中断");
            Thread.currentThread().interrupt();
        } finally {
            if (isOk) {
                log.info("第" + index + "次，释放锁");
                lock.unlock();
            }
        }
    }

    @SneakyThrows
    @Override
    @RedisLock(lockName = "handleStockRedisLockAnnotation")
    public void handleStockRedisLockAnnotation(Integer index, Integer buyNum, Long sleepTime) {
        handle(index, buyNum, sleepTime);
    }

    private void handle(Integer index, Integer buyNum, Long sleepTime) throws InterruptedException {
        int stockNum = redissonUtil.get("maple:stock");
        log.info("第" + index + "次，购买商品" + buyNum + "，获取库存，库存数：" + stockNum);
        Thread.sleep(sleepTime);
        redissonUtil.set("maple:stock", stockNum - buyNum);
        log.info("第" + index + "次，处理后的库存数：" + redissonUtil.get("maple:stock"));
    }
}
