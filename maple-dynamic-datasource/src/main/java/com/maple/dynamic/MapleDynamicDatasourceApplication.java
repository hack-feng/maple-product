package com.maple.dynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.maple.dynamic.test.mapper", "com.maple.dynamic.test2.mapper"})
public class MapleDynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleDynamicDatasourceApplication.class, args);
    }

}
