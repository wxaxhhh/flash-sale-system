package com.example.flashsalesystem.service;
import com.example.flashsalesystem.MQProducer;
import com.example.flashsalesystem.entity.SeckillOrder;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.redisson.api.RLock;
import java.util.concurrent.TimeUnit;

@Service
public class SeckillService {
    @Autowired
    private SeckillOrderService seckillOrderService;
    @Autowired
    private SeckillProductService seckillProductService;
    @Autowired
    private SeckillActivityService seckillActivityService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private MQProducer mqProducer;

    @Autowired
    private static final String LUA_SCRIPT =
            "local key = KEYS[1] " +
                    "local stock = redis.call('get', key) " +
                    "if stock and tonumber(stock) > 0 then " +
                    "    redis.call('decr', key) " +
                    "    return 1 " +
                    "else " +
                    "    return 0 " +
                    "end";

    private final DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);


    @Transactional
    public Map<String,Object> executeSeckill(Map<String,Object> params) throws InterruptedException{
        Map<String,Object> result = new HashMap<>();
        Long userId= ((Number)params.get("userId")).longValue();
        Long activityId= ((Number)params.get("activityId")).longValue();
        Long productId= ((Number)params.get("productId")).longValue();
        RLock lock=redissonClient.getLock("lock:product:"+productId);
        try {
            System.out.println(Thread.currentThread().getName() + " 尝试拿锁");
            if(lock.tryLock(5,10,TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " 拿到锁，开始秒杀");

                Integer Status = seckillActivityService.findById(activityId).getStatus();
                System.out.println("活动状态：" + Status);

                if (Status == 2) {
                    System.out.println("活动进行中，检查是否重复");

                    SeckillOrder exist = seckillOrderService.findByUserActivityProduct(userId, activityId, productId);
                    System.out.println("exist 是否为 null: " + (exist == null));
                    if (exist == null) {
                        // 扣库存
                        System.out.println("准备扣库存，key: product_stock:" + productId);
                        String key = "product_stock:" + productId;
                        Long luaResult = redisTemplate.execute(redisScript, Collections.singletonList(key));
                        System.out.println("Lua 执行结果: " + luaResult);
                        System.out.println("扣库存完成，剩余: " + redisTemplate.opsForValue().get(key));
                        if (luaResult == null || luaResult == 0) {
                            result.put("code", 500);
                            result.put("status", "fail");
                            result.put("msg", "库存不足");
                            return result;
                        } else {
                            // 组装订单消息，发送给 RocketMQ，异步处理
                            Map<String, Object> orderMsg = new HashMap<>();
                            orderMsg.put("userId", userId);
                            orderMsg.put("activityId", activityId);
                            orderMsg.put("productId", productId);

                            mqProducer.sendObject("seckill_order_topic", orderMsg);

                            result.put("code", 200);
                            result.put("status", "success");
                            result.put("msg", "下单请求已接收，正在处理");
                            System.out.println(Thread.currentThread().getName() + " 释放锁");
                        }
                    }
                }


            }
        }catch (Exception e){
            result.put("code", 500);
            result.put("status","fail");
            e.printStackTrace();
        }finally {
            if(lock != null && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
        return result;
    }
}
