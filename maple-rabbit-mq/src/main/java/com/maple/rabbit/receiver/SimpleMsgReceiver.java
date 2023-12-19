package com.maple.rabbit.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangfuzeng
 * @date 2023/12/18
 */
@Component
@Slf4j
public class SimpleMsgReceiver {


    /**
     * 监听simpleQueue队列的消息，进行消费
     */
    @RabbitListener(queues = "simpleQueue")
    public void simpleMsgHandle(String msg) {
        log.info("SimpleMsgReceiver消费消息: " + msg);
        throw new RuntimeException("模拟异常，进行重试");
    }
}
