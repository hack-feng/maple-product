package com.maple.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 */
@SpringBootApplication
@ComponentScan("com.maple")
public class MapleFileUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleFileUploadApplication.class, args);
    }

}
