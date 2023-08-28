package com.maple.redis.util;

import org.redisson.api.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 笑小枫  https://xiaoxiaofeng.com/
 * @date 2023/8/28
 */
@Component
public class RedissonUtil {

    private final RedissonClient client;

    public RedissonUtil(RedissonClient client) {
        this.client = client;
    }

    /**
     * 写入缓存
     */
    public <T> void set(String key, T value) {
        RBucket<T> rBucket = client.getBucket(key);
        rBucket.set(value);
    }

    /**
     * 写入缓存设置时效时间
     */
    public <T> void set(String key, T value, long seconds) {
        RBucket<T> rBucket = client.getBucket(key);
        rBucket.set(value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 写入缓存设置时效时间，并指定时间类型
     */
    public <T> void set(String key, T value, long timeToLive, TimeUnit timeUnit) {
        RBucket<T> rBucket = client.getBucket(key);
        rBucket.set(value, timeToLive, timeUnit);
    }

    /**
     * 获取缓存内容
     */
    public <T> T get(String key) {
        RBucket<T> rBucket = client.getBucket(key);
        return rBucket.get();
    }

    /**
     * 删除缓存
     */
    public <T> boolean delete(String key) {
        RBucket<T> rBucket = client.getBucket(key);
        return rBucket.delete();
    }

    /**
     * 删除缓存，并返回缓存的值
     */
    public <T> T getAndDelete(String key) {
        RBucket<T> rBucket = client.getBucket(key);
        return rBucket.getAndDelete();
    }

    /**
     * 获取原子递增Long类型值
     */
    public RAtomicLong getAtomicLong(String key) {
        return client.getAtomicLong(key);
    }

    /**
     * 获取一个普通的RMap实例
     */
    public <K, V> RMap<K, V> getMap(String key) {
        return client.getMap(key);
    }

    /**
     * 获取一个带缓存自动过期功能的RMapCache实例
     */
    public <K, V> RMapCache<K, V> getMapCache(String key) {
        return client.getMapCache(key);
    }

    /**
     * 获取RSet实例
     */
    public <T> RSet<T> getSet(String key) {
        return client.getSet(key);
    }

    /**
     * 获取RList实例
     */
    public <T> RList<T> getList(String key) {
        return client.getList(key);
    }

    /**
     * 获取RQueue实例
     */
    public <T> RQueue<T> getQueue(String key) {
        return client.getQueue(key);
    }

    /**
     * 获取Redis锁
     * 基于Redis的Redisson分布式可重入锁RLock Java对象实现了java.util.concurrent.locks.Lock接口。
     */
    public RLock getLock(String key) {
        return client.getLock(key);
    }

    /**
     * 获取Redis公平锁
     * 它保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     */
    public RLock getFairLock(String key) {
        return client.getFairLock(key);
    }

    /**
     * Redis事务
     * Redisson事务通过分布式锁保证了连续写入的原子性，同时在内部通过操作指令队列实现了Redis原本没有的提交与滚回功能。
     */
    public RTransaction getTransaction() {
        TransactionOptions options = TransactionOptions.defaults()
                // 设置参与本次事务的主节点与其从节点同步的超时时间, 默认值是5秒。
                .syncSlavesTimeout(5, TimeUnit.SECONDS)
                // 处理结果超时, 默认值是3秒。
                .responseTimeout(3, TimeUnit.SECONDS)
                // 命令重试等待间隔时间。仅适用于未发送成功的命令,  默认值是1.5秒。
                .retryInterval(2, TimeUnit.SECONDS)
                // 命令重试次数。仅适用于未发送成功的命令, 默认值是3次。
                .retryAttempts(3)
                // 事务超时时间。如果规定时间内没有提交该事务则自动滚回, 默认值是5秒。
                .timeout(5, TimeUnit.SECONDS);
        return client.createTransaction(options);
    }
}
