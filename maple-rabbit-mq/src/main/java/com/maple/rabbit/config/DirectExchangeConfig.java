package com.maple.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuzeng
 * @date 2023/12/18
 */
@Configuration
public class DirectExchangeConfig {
    /**
     * 模拟广播发送消息
     */
    @Bean
    public Queue directQueueA() {
        return new Queue("directQueue.A");
    }

    @Bean
    public Queue directQueueB() {
        return new Queue("directQueue.B");
    }

    @Bean
    public Queue directQueueC() {
        return new Queue("directQueue.C");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding bindingDirectExchangeA(Queue directQueueA, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueA).to(directExchange).with("directRoutingKeyA");
    }

    @Bean
    public Binding bindingDirectExchangeB(Queue directQueueB, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueB).to(directExchange).with("directRoutingKeyB");
    }

    @Bean
    public Binding bindingDirectExchangeC(Queue directQueueC, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueC).to(directExchange).with("directRoutingKeyB");
    }
    
}
