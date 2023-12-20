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
public class MoreToMoreSender {
    private final AmqpTemplate rabbitTemplate;

    /**
     * 模拟多个生产者和多个消费者同时工作
     * 生产者1
     */
    public void sendOne(String msg) {
        rabbitTemplate.convertAndSend("moreToMoreQueue", "MoreToMoreSender.sendOne:" + msg);
        log.info("MoreToMoreSender sendOne 发送消息成功：" + msg);
    }

    public void sendTwo(String msg) {
        rabbitTemplate.convertAndSend("moreToMoreQueue", "MoreToMoreSender.sendTwo:" + msg);
        log.info("MoreToMoreSender sendTwo 发送消息成功：" + msg);
    }
}
