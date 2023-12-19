package com.maple.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuzeng
 * @date 2023/12/18
 */
@Configuration
public class TopicExchangeConfig {
    /**
     * 模拟广播发送消息
     */
    @Bean
    public Queue topicQueueA() {
        return new Queue("topicQueue.A");
    }

    @Bean
    public Queue topicQueueB() {
        return new Queue("topicQueue.B");
    }

    @Bean
    public Queue topicQueueC() {
        return new Queue("topicQueue.C.1");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingTopicExchangeA(Queue topicQueueA, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueueA).to(topicExchange).with("topicQueue.A");
    }

    @Bean
    public Binding bindingTopicExchangeB(Queue topicQueueB, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with("topicQueue.*");
    }

    @Bean
    public Binding bindingTopicExchangeC(Queue topicQueueC, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueueC).to(topicExchange).with("topicQueue.#");
    }


}
