package com.maple.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.maple.es.mapper")
public class MapleEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleEsApplication.class, args);
    }

}
