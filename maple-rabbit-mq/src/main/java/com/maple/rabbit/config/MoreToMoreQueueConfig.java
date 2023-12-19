package com.maple.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuzeng
 * @date 2023/12/18
 */
@Configuration
public class MoreToMoreQueueConfig {

    /**
     * 模拟多个生产者和多个消费者同时工作
     */
    @Bean
    public Queue moreToMoreQueue() {
        return new Queue("moreToMoreQueue");
    }
}
