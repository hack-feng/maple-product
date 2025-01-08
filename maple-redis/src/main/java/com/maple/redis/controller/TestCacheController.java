package com.maple.redis.controller;

import com.maple.redis.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangfuzeng
 * @date 2025/1/7
 */
@Slf4j
@RestController
@RequestMapping("/cache")
public class TestCacheController {

    /**
     * 获取简单缓存数据。
     *
     * <p>通过@Cacheable注解，该方法的结果会被缓存到名为"cache:simpleCache"的缓存中。
     * 如果在缓存中找到相同请求的结果，将直接返回缓存的值，避免重复执行方法体中的逻辑。
     *
     * <p>方法内部，使用Thread.sleep(5000)模拟了一个耗时操作，
     */
    @GetMapping("/simpleCache")
    @Cacheable(cacheNames = "cache:simpleCache")
    public String simpleCache() throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了simpleCache方法");
        return "test";
    }

    /**
     * 如果缓存中存在对应的ID，则直接从缓存中获取结果，避免重复执行耗时操作。
     * 如果缓存中不存在，则执行方法体中的逻辑，将结果存入缓存并返回。
     * 方法执行过程中，通过Thread.sleep模拟了一个耗时操作。
     */
    @GetMapping("/{id}")
    @Cacheable(cacheNames = "cache:cacheByKey", key = "#id")
    public Integer cacheByKey(@PathVariable("id") Integer id) throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了cacheByKey方法" + id);
        return id;
    }

    /**
     * <p>该方法使用@Caching注解集成了多个缓存策略：</p>
     * <ul>
     *     <li>
     *         当方法返回值为null时（即缓存穿透情况），使用名为"cacheNullManager"的CacheManager进行缓存处理，
     *         缓存名称为"cache2:cacheByKey"，缓存键为传入的用户ID，并设置缓存过期时间为10分钟。
     *         这通过@Cacheable注解的cacheManager属性指定缓存管理器，unless属性设置缓存条件（当结果为null时缓存）。
     *     </li>
     *     <li>
     *         当方法返回值不为null时，使用默认的CacheManager进行缓存处理，
     *         缓存名称和键的设置与上述相同，但此时缓存管理器为默认配置。
     *         这通过另一个@Cacheable注解实现，其unless属性设置为当结果为null时不缓存。
     *     </li>
     * </ul>
     *
     * <p>在方法执行过程中，通过Thread.sleep模拟了一个耗时操作。</p>
     */
    @Caching(
            cacheable = {
                    //result为null时,属于缓存穿透情况，使用cacheNullManager缓存管理器进行缓存，并且设置过期时间为10分钟。
                    @Cacheable(cacheNames = "cache2:cacheByKey", key = "#id", unless = "#result != null", cacheManager = "cacheNullManager"),
                    //result不为null时,使用默认缓存管理器进行缓存。
                    @Cacheable(cacheNames = "cache2:cacheByKey", key = "#id", unless = "#result == null")
            }
    )
    @GetMapping("/cacheMore/{id}")
    public User cacheMore(@PathVariable("id") Integer id) throws InterruptedException {
        Thread.sleep(5000);
        if (id > 100) {
            return null;
        } else {
            return new User(id, "zhangsan");
        }
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
     *                              <p>该方法使用@PostMapping注解定义了一个处理HTTP POST请求的映射路径"/cacheByUserIdGt100"。
     *                              通过@Cacheable注解实现了缓存功能，当请求的用户ID大于100时，会触发缓存机制。
     *                              缓存的名称设置为"cache2:cacheByUser"，缓存的键为传入的用户对象的ID。
     *                              如果缓存中已存在对应的用户数据，则直接从缓存中获取并返回，避免重复执行耗时操作。
     *                              如果缓存中不存在，则执行方法体中的逻辑，将结果存入缓存并返回。
     *                              在方法执行过程中，通过Thread.sleep模拟了一个耗时操作。
     */
    @PostMapping("/cacheByUserIdGt100")
    @Cacheable(cacheNames = "cache2:cacheByUser", key = "#user.id", condition = "#user.id > 100")
    public User cacheByUserIdGt100(@RequestBody User user) throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了cacheByUser方法" + user.getId());
        return user;
    }

    /**
     * 更新用户信息。
     * <p>
     * 使用@CachePut注解将更新后的用户信息存入缓存中。
     * 缓存的名称设置为"cache2:cacheByUser"，缓存的键为传入的User对象的ID。
     * 如果缓存中已存在对应的用户数据，则更新缓存中的值；如果不存在，则创建新的缓存条目。
     * 在方法执行过程中，通过Thread.sleep模拟了一个耗时操作。
     */
    @PostMapping("/updateUser")
    @CachePut(cacheNames = "cache2:cacheByUser", key = "#user.id")
    public User saveUser(@RequestBody User user) throws InterruptedException {
        Thread.sleep(5000);
        log.info("执行了saveUser方法" + user.getId());
        return user;
    }

    /**
     * 删除指定ID的用户，并从缓存中移除对应的数据。
     * <p>
     * 使用@CacheEvict注解用于从缓存中移除指定ID的用户数据。
     * 缓存的名称设置为"cache2:cacheByUser"，缓存的键为传入的用户ID。
     * 在执行删除操作前，方法通过Thread.sleep模拟了一个耗时操作。
     */
    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "cache2:cacheByUser", key = "#id")
    public void deleteUser(@PathVariable("id") Integer id) throws InterruptedException {
        Thread.sleep(10000);
        log.info("执行了deleteUser方法" + id);
    }
}
