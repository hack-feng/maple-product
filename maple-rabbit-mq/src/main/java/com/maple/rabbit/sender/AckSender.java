package com.maple.rabbit.sender;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhangfuzeng
 * @date 2023/12/19
 */
@Component
@AllArgsConstructor
@Slf4j
public class AckSender {

    private final AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        rabbitTemplate.convertAndSend("ackExchange", "ackRoutingKey", msg);
        log.info("AckSender 发送消息成功：" + msg);
    }
}
