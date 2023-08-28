package com.maple.redis.controller;

import com.alibaba.fastjson.JSON;
import com.maple.redis.util.RedisUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @author 笑小枫
 * @date 2022/7/20
 */
@RestController
@RequestMapping("/redis")
public class TestRedisController {

    private final RedisUtil redisUtil;

    public TestRedisController(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 插入String类型的数据到redis
     */
    @PutMapping("/insertStr")
    public void insertStr(String key, String value) {
        redisUtil.set(key, value);
    }

    /**
     * 根据key获取redis的数据
     */
    @PostMapping("/getStr")
    public String getStr(String key) {
        return String.valueOf(redisUtil.get(key));
    }

    /**
     * 根据key删除redis的数据
     */
    @DeleteMapping("/deleteStr")
    public Boolean deleteStr(String key) {
        redisUtil.remove(key);
        return redisUtil.exists(key);
    }

    /**
     * 模拟操作Map集合的数据
     */
    @PostMapping("/operateMap")
    public Object operateMap() {
        redisUtil.hmSet("maple:map", "xiaofeng", "笑小枫");
        return redisUtil.hmGet("maple:map", "xiaofeng");
    }

    /**
     * 模拟操作List集合的数据
     */
    @PostMapping("/operateList")
    public String operateList() {
        String listKey = "maple:list";
        redisUtil.lPush(listKey, "小枫");
        redisUtil.lPush(listKey, "小明");
        redisUtil.lPush(listKey, "小枫");
        return JSON.toJSONString(redisUtil.lRange(listKey, 0, 2));
    }

    /**
     * 模拟操作Set集合的数据
     */
    @PostMapping("/operateSet")
    public String operateSet() {
        String listKey = "maple:set";
        redisUtil.addSet(listKey, "小枫");
        redisUtil.addSet(listKey, "小明");
        redisUtil.addSet(listKey, "小枫");
        System.out.println("集合中是否包含小枫" + redisUtil.isMember(listKey, "小枫"));
        System.out.println("集合中是否包含小红" + redisUtil.isMember(listKey, "小红"));
        return JSON.toJSONString(redisUtil.setMembers(listKey));
    }

    /**
     * 模拟操作ZSet有序集合的数据
     */
    @PostMapping("/operateZSet")
    public String operateZSet() {
        String listKey = "maple:zSet";
        redisUtil.zAdd(listKey, "小枫", 8);
        redisUtil.zAdd(listKey, "小明", 1);
        redisUtil.zAdd(listKey, "小红", 12);
        redisUtil.zAdd(listKey, "大明", 5);
        redisUtil.zAdd(listKey, "唐三", 10);
        redisUtil.zAdd(listKey, "小舞", 9);
        // 降序获取source最高的5条数据
        return JSON.toJSONString(redisUtil.reverseRange(listKey, 0L, 4L));
    }
}
