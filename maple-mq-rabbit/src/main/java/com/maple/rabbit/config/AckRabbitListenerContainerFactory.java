package com.maple.rabbit.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/12/19
 */
@Configuration
public class AckRabbitListenerContainerFactory {

    @Bean(value = "ackListenerContainerFactory", name = "ackListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory ackListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(ackConnectionFactory());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        // 设置消息为手动确认
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * 获取rabbitmq连接.
     *
     * @return 返回rabbitmq连接.
     */
    @Bean(value = "ackConnectionFactory")
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public CachingConnectionFactory ackConnectionFactory() {
        return new CachingConnectionFactory();
    }
}
