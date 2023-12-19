package com.maple.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuzeng
 * @date 2023/12/19
 */
@Configuration
public class DeadLetterQueueConfig {
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("deadLetterQueue")
                .withArgument("x-dead-letter-exchange", "dlx-exchange")
                .build();
    }

    @Bean
    public Queue normalQueue() {
        //声明该队列的死信消息发送到的 交换机 (队列添加了这个参数之后会自动与该交换机绑定,并设置路由键,不需要开发者手动设置)
        return QueueBuilder.durable("normalQueue")
                .withArgument("x-message-ttl", 5000)
                .withArgument("x-dead-letter-exchange", "dlx-exchange")
                .build();
    }

    @Bean
    public TopicExchange dlxExchange() {
        return new TopicExchange("dlx-exchange");
    }

    @Bean
    public Binding dlxBinding(Queue deadLetterQueue, TopicExchange dlxExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(dlxExchange).with("#");
    }

}
