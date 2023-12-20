package com.maple.rabbit.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/12/18
 */
@Component
@Slf4j
public class TopicExchangeReceiver {

    /**
     * 监听topicQueue.A队列的消息，进行消费
     */
    @RabbitListener(queues = "topicQueue.A")
    public void topicHandleA(String msg) {
        log.info("topicHandleA消费消息: " + msg);
    }

    /**
     * 监听topicQueue.B队列的消息，进行消费
     */
    @RabbitListener(queues = "topicQueue.B")
    public void topicHandleB(String msg) {
        log.info("topicHandleB消费消息: " + msg);
    }

    /**
     * 监听topicQueue.C.1队列的消息，进行消费
     */
    @RabbitListener(queues = "topicQueue.C.1")
    public void topicHandleC(String msg) {
        log.info("topicHandleC消费消息: " + msg);
    }
}
