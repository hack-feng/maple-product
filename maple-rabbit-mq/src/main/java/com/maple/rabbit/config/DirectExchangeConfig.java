package com.maple.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/12/18
 */
@Configuration
public class DirectExchangeConfig {
    /**
     * 使用直接交换机发送消息
     * directRoutingKeyA 单播路由
     * directRoutingKeyB 广播路由
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
