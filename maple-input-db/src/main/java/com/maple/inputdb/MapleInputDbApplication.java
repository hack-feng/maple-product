package com.maple.inputdb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 */
@ServletComponentScan
@MapperScan("com.maple.inputdb.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MapleInputDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleInputDbApplication.class, args);
    }
}
