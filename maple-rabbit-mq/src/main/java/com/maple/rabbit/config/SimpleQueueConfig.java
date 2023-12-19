package com.maple.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhangfuzeng
 * @date 2023/12/18
 */
@Configuration
public class SimpleQueueConfig {

    /**
     * 模拟使用默认的交换机，进行消息发布消费
     */
    @Bean
    public Queue simpleQueue() {
        return new Queue("simpleQueue");
    }
}
