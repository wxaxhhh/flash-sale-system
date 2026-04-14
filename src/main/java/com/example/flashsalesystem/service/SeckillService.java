package com.example.flashsalesystem.service;
import com.example.flashsalesystem.entity.SeckillOrder;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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



    @Transactional
    public Map<String,Object> executeSeckill(Map<String,Object> params){
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
                        Long remain = redisTemplate.opsForValue().decrement("product_stock:" + productId);
                        Thread.sleep(1000); // 睡 3 秒
                        System.out.println("扣库存完成，剩余: " + remain);
                        if (remain < 0) {
                            result.put("code", 500);
                            result.put("status", "fail");
                            return result;
                        }else {
                            SeckillOrder seckillOrder = new SeckillOrder();
                            seckillOrder.setActivityId(activityId);
                            seckillOrder.setUserId(userId);
                            seckillOrder.setProductId(productId);
                            seckillOrderService.add(seckillOrder);
                            result.put("code", 200);
                            result.put("status", "success");
                            result.put("orderId", seckillOrder.getId());
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
