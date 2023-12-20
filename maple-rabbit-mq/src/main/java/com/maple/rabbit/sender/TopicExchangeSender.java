package com.maple.rabbit.sender;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/12/18
 */
@Component
@AllArgsConstructor
@Slf4j
public class TopicExchangeSender {
    private final AmqpTemplate rabbitTemplate;

    public void sendA(String msg) {
        rabbitTemplate.convertAndSend("topicExchange", "topicQueue.A", "sendA:" + msg);
        log.info("TopicExchangeSender sendA 发送消息成功：" + msg);
    }

    public void sendB(String msg) {
        rabbitTemplate.convertAndSend("topicExchange", "topicQueue.B", "sendB:" + msg);
        log.info("TopicExchangeSender sendB 发送消息成功：" + msg);
    }

    public void sendC(String msg) {
        rabbitTemplate.convertAndSend("topicExchange", "topicQueue.C.1", "sendC:" + msg);
        log.info("TopicExchangeSender sendC 发送消息成功：" + msg);
    }
}
