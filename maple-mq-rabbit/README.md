## 模块简介

本模块主要介绍了SpringBoot集成RabbitMQ消息队列，以案例的形式展示了不同模式的交换机实现过程，演示了死信队列和延时队列应该怎么实现。

## 目录简介

* config 存放交换机、队列的创建配置
* controller 模拟发送消息调用接口
* receiver 消息消费者
* sender 消息生产者
* util 封装工具类

## 队列案例

xxx 代表后缀

例如：Simple xxx

其对应的完整的一套案例包括

~~~
SimpleQueueConfig.java
SimpleMsgReceiver.java
SimpleMsgSender.java
SendMsgController.java下的simpleQueueSend()方法
~~~

### 简单队列

Simple xxx

### 多生产者多消费者

MoreToMore xxx

### 直接交换机 Direct Exchange

Direct xxx

### 扇形交换机 Fanout Exchange

Fanout xxx

### 主题交换机 Topic Exchange

Topic xxx

### 应答模式ACK

Ack xxx

### 死信队列

DeadLetter xxx

### 延时队列

Delay Queue xxx
