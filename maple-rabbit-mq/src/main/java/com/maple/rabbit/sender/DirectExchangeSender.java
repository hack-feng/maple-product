package com.maple.rabbit.sender;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhangfuzeng
 * @date 2023/12/18
 */
@Component
@AllArgsConstructor
@Slf4j
public class DirectExchangeSender {

    private final AmqpTemplate rabbitTemplate;

    public void sendA(String msg) {
        rabbitTemplate.convertAndSend("directExchange", "directRoutingKeyA", "sendA:" + msg);
        log.info("DirectExchangeSender sendA 发送消息成功：" + msg);
    }

    public void sendB(String msg) {
        rabbitTemplate.convertAndSend("directExchange", "directRoutingKeyB", "sendB:" + msg);
        log.info("DirectExchangeSender sendB 发送消息成功：" + msg);
    }
}
