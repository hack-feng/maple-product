package com.maple.rabbit.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/12/19
 */
@Component
@Slf4j
public class DeadLetterReceiver {
    /**
     * 监听normalQueue队列的消息，进行消费，模拟消费抛出异常，让消息进入死信队列
     */
    @RabbitListener(queues = "normalQueue")
    public void normalHandle(String msg) {
        log.info("DeadLetterReceiver normalHandle 消费消息: " + msg);
        throw new RuntimeException("DeadLetterReceiver normalHandle 消费消息异常，测试死信队列");
    }

    /**
     * 处理进入到死信队列的消息
     */
    @RabbitListener(queues = "deadLetterQueue")
    public void deadLetterHandle(String msg) {
        log.info("DeadLetterReceiver deadLetterHandle 消费消息: " + msg);
    }
}
