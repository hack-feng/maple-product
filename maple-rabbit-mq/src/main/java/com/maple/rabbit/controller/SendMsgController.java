package com.maple.rabbit.controller;

import com.maple.rabbit.sender.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangfuzeng
 * @date 2023/12/18
 */
@RestController
@AllArgsConstructor
public class SendMsgController {

    private final SimpleMsgSender simpleMsgSender;

    private final MoreToMoreSender moreToMoreSender;

    private final FanoutExchangeSender fanoutExchangeSender;

    private final TopicExchangeSender topicExchangeSender;

    private final DirectExchangeSender directExchangeSender;

    private final AckSender ackSender;

    private final DeadLetterSender deadLetterSender;

    private final DelaySender delaySender;

    /**
     * 模拟使用默认的交换机，调用消息发送
     */
    @GetMapping("/simpleQueueSend")
    public String simpleQueueSend(String msg) {
        simpleMsgSender.send(msg);
        return "发送成功";
    }

    @GetMapping("/moreToMoreSend")
    public String moreToMoreSend(String msg) {
        moreToMoreSender.sendOne(msg);
        moreToMoreSender.sendTwo(msg);
        return "发送成功";
    }

    @GetMapping("/fanoutExchangeSend")
    public String fanoutExchangeSend(String msg) {
        fanoutExchangeSender.send(msg);
        return "发送成功";
    }

    @GetMapping("/topicExchangeSend")
    public String topicExchangeSend(String msg) {
        topicExchangeSender.sendA(msg);
        topicExchangeSender.sendB(msg);
        topicExchangeSender.sendC(msg);
        return "发送成功";
    }

    @GetMapping("/directExchangeSend")
    public String directExchangeSend(String msg) {
        directExchangeSender.sendA(msg);
        directExchangeSender.sendB(msg);
        return "发送成功";
    }

    @GetMapping("/ackSend")
    public String ackSend(String msg) {
        ackSender.send(msg);
        return "发送成功";
    }

    @GetMapping("/deadLetterSend")
    public String deadLetterSend(String msg) {
        deadLetterSender.send(msg);
        return "发送成功";
    }

    @GetMapping("/delaySend")
    public String delaySend(String msg) {
        delaySender.send(msg);
        return "发送成功";
    }


}
