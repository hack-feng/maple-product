package com.maple.rabbit.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author zhangfuzeng
 * @date 2023/12/19
 */
@Component
@Slf4j
public class AckReceiver {

    /**
     * 监听simpleQueue队列的消息，进行消费
     */
    @RabbitListener(queues = {"ackQueue"}, containerFactory = "ackListenerContainerFactory")
    public void ackHandle(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("AckReceiver消费消息: " + msg);
        channel.basicAck(deliveryTag, false);
    }
}
