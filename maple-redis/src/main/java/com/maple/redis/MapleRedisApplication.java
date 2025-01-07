package com.maple.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 */
@SpringBootApplication
public class MapleRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleRedisApplication.class, args);
    }

}
