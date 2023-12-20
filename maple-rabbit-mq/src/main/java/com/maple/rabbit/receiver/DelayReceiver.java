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
public class DelayReceiver {
    
    @RabbitListener(queues = "delayDeadQueue")
    public void delayHandle(String msg) {
        log.info("DelayReceiver delayHandle 消费消息: " + msg);
    }
}
