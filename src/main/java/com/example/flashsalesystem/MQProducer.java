package com.example.flashsalesystem;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class MQProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(String topic, String msg) {
        rocketMQTemplate.convertAndSend(topic, msg);
        System.out.println("MQ发送成功: " + msg);
    }
    public void sendObject(String topic, Object obj) {
        rocketMQTemplate.convertAndSend(topic, obj);
        System.out.println("MQ发送成功: " + obj);
    }
    public void sendDelay(String topic, Object obj, int delayLevel) {
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(obj).build(), 3000, delayLevel);
        System.out.println("延迟消息发送成功，延迟等级: " + delayLevel + "，内容: " + obj);
    }
}