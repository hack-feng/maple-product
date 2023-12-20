package com.maple.rabbit.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/12/19
 */
@Component
@Slf4j
public class AckReceiver {

    /**
     * 监听ackQueue队列的消息，进行消费
     */
    @RabbitListener(queues = {"ackQueue"}, containerFactory = "ackListenerContainerFactory")
    public void ackHandle(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("AckReceiver消费消息: " + msg);
        // 同意签收
        // deliveryTag（唯一标识 ID）：当一个消费者向RabbitMQ 注册后，会建立起一个 Channel ，向消费者推送消息，这个方法携带了一个deliveryTag， 它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，deliveryTag的范围仅限于当前 Channel。
        // multiple：为了减少网络流量，手动确认可以被批处理，当该参数为true时，则可以一次性确认 deliveryTag小于等于传入值的所有消息
        channel.basicAck(deliveryTag, false);
        // 拒绝签收
        // deliveryTag：同basicAck
        // multiple：同basicAck
        // requeue：重回队列。如果设置为true，则消息重新回到queue，服务端会重新发送该消息给消费端
//        channel.basicNack(deliveryTag, false, true);
    }
}
