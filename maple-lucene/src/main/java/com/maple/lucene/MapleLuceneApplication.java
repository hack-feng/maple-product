package com.maple.lucene;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 笑小枫 <https://xiaoxiaofeng.com/>
 */
@SpringBootApplication
@ComponentScan("com.maple")
@MapperScan({"com.maple.**.mapper"})
public class MapleLuceneApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleLuceneApplication.class, args);
    }

}
