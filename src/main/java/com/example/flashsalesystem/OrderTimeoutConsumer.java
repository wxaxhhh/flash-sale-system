package com.example.flashsalesystem;

import com.example.flashsalesystem.entity.SeckillOrder;
import com.example.flashsalesystem.service.SeckillOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RocketMQMessageListener(topic = "order_timeout_topic", consumerGroup = "order-timeout-consumer-group")
public class OrderTimeoutConsumer implements RocketMQListener<Map<String, Object>> {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onMessage(Map<String, Object> msg) {
        Long orderId = ((Number) msg.get("orderId")).longValue();
        Long productId = ((Number) msg.get("productId")).longValue();

        System.out.println("\n========== 延迟消息到达 ==========");
        System.out.println("5秒已到，检查订单: " + orderId);

        SeckillOrder order = seckillOrderService.findById(orderId);

        if (order != null && order.getStatus() == 0) {
            order.setStatus(2); // 超时关闭
            seckillOrderService.update(order);
            System.out.println("订单 " + orderId + " 超时未支付，已关闭");

            String stockKey = "product_stock:" + productId;
            redisTemplate.opsForValue().increment(stockKey);
            System.out.println("库存已回滚，商品ID: " + productId);
        } else {
            System.out.println("订单 " + orderId + " 状态为 " + (order != null ? order.getStatus() : "null") + "，无需处理");
        }
        System.out.println("===================================\n");
    }
}