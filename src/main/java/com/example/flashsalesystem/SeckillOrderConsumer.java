package com.example.flashsalesystem;

import com.example.flashsalesystem.entity.SeckillOrder;
import com.example.flashsalesystem.service.SeckillOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@RocketMQMessageListener(topic = "seckill_order_topic", consumerGroup = "seckill-order-consumer-group")
public class SeckillOrderConsumer implements RocketMQListener<Map<String, Object>> {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private MQProducer mqProducer;

    @Override
    public void onMessage(Map<String, Object> msg) {
        try {
            System.out.println("\n========== 消费者开始处理 ==========");
            System.out.println("收到订单消息: " + msg);

            Long userId = ((Number) msg.get("userId")).longValue();
            Long activityId = ((Number) msg.get("activityId")).longValue();
            Long productId = ((Number) msg.get("productId")).longValue();

            SeckillOrder order = new SeckillOrder();
            order.setUserId(userId);
            order.setActivityId(activityId);
            order.setProductId(productId);

            seckillOrderService.add(order);
            System.out.println("异步订单创建成功，订单ID: " + order.getId());

            Map<String, Object> delayMsg = new HashMap<>();
            delayMsg.put("orderId", order.getId());
            delayMsg.put("productId", productId);
            mqProducer.sendDelay("order_timeout_topic", delayMsg, 2); // 2 = 5秒，测试用
            System.out.println("延迟消息发送成功（5秒后检查）");
            System.out.println("===================================\n");

        } catch (Exception e) {
            System.err.println("\n消费者处理失败！！！");
            e.printStackTrace();
        }
    }
}