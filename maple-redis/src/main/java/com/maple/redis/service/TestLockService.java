package com.maple.redis.service;

/**
 * @author zhangfuzeng
 * @date 2025/1/9
 */
public interface TestLockService {
    
    void handleStock(Integer index, Integer buyNum, Long sleepTime);

    void handleStockSynchronized(Integer index, Integer buyNum, Long sleepTime);
    
    void handleStockRedisLock(Integer index, Integer buyNum, Long sleepTime);
    
    void handleStockRedisLockAnnotation(Integer index, Integer buyNum, Long sleepTime);
    
}
