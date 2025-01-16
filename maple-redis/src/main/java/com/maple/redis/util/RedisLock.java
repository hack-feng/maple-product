package com.maple.redis.util;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 使用redis进行分布式锁
 * @author 笑小枫
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**
     * redis锁 名字
     */
    String lockName() default "";

    /**
     * redis锁 key 支持spel表达式
     */
    String key() default "";

    /**
     * 等待毫秒数,默认为5000毫秒
     *
     * @return 最大等待毫秒数
     */
    int waitTime() default 5000;

    /**
     * 过期毫秒数,默认为-1 不自动解锁
     *
     * @return 轮询锁的时间
     */
    int leaseTime() default -1;

    /**
     * 超时时间单位
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
