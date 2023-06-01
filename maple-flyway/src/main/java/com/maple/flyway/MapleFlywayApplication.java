package com.maple.flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangfuzeng
 */
@SpringBootApplication
@ComponentScan("com.maple")
public class MapleFlywayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleFlywayApplication.class, args);
    }

}
