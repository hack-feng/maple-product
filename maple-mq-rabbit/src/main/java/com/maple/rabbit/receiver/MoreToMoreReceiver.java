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
public class MoreToMoreReceiver {

    /**
     * 监听moreToMoreQueue队列的消息，进行消费
     */
    @RabbitListener(queues = "moreToMoreQueue")
    public void moreToMoreHandleOne(String msg) {
        log.info("moreToMoreHandleOne消费消息: " + msg);
    }

    /**
     * 监听moreToMoreQueue队列的消息，进行消费
     */
    @RabbitListener(queues = "moreToMoreQueue")
    public void moreToMoreHandleTwo(String msg) {
        log.info("moreToMoreHandleTwo消费消息: " + msg);
    }

    /**
     * 监听moreToMoreQueue队列的消息，进行消费
     */
    @RabbitListener(queues = "moreToMoreQueue")
    public void moreToMoreHandleThree(String msg) {
        log.info("moreToMoreHandleThree消费消息: " + msg);
    }
}
