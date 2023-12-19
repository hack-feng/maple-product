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
public class DirectExchangeReceiver {

    /**
     * 监听simpleQueue队列的消息，进行消费
     */
    @RabbitListener(queues = "directQueue.A")
    public void directHandleA(String msg) {
        log.info("directHandleA消费消息: " + msg);
    }

    /**
     * 监听simpleQueue队列的消息，进行消费
     */
    @RabbitListener(queues = "directQueue.B")
    public void directHandleB(String msg) {
        log.info("directHandleB消费消息: " + msg);
    }


    /**
     * 监听simpleQueue队列的消息，进行消费
     */
    @RabbitListener(queues = "directQueue.C")
    public void directHandleC(String msg) {
        log.info("directHandleC消费消息: " + msg);
    }
}
