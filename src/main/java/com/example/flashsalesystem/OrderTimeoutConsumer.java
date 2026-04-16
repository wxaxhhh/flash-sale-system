package com.example.flashsalesystem;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RocketMQMessageListener(topic = "order_timeout_topic", consumerGroup = "order-timeout-consumer-group")
public class OrderTimeoutConsumer implements RocketMQListener<Map<String, Object>> {

    @Override
    public void onMessage(Map<String, Object> msg) {
        System.out.println("\n========== 延迟消息到达 ==========");
        System.out.println("收到延迟消息，订单ID: " + msg.get("orderId"));
        System.out.println("这里应该检查订单支付状态，未支付则关单、回滚库存");
        System.out.println("===================================\n");
    }
}