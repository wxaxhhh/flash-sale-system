package com.example.flashsalesystem.service;
import com.example.flashsalesystem.entity.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.Map;

@Service
public class SeckillService {
    @Autowired
    private SeckillOrderService seckillOrderService;
    @Autowired
    private SeckillProductService seckillProductService;
    @Autowired
    private SeckillActivityService seckillActivityService;

    @Transactional
    public Map<String,Object> executeSeckill(Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        Long userId= ((Number)params.get("userId")).longValue();
        Long activityId= ((Number)params.get("activityId")).longValue();
        Long productId= ((Number)params.get("productId")).longValue();
        try {
            Integer Status = seckillActivityService.findById(activityId).getStatus();
            Integer stock = seckillProductService.findById(productId).getStock();

            if (Status == 2) {
                if(stock>0){
                    if(seckillOrderService.findByUserActivityProduct(userId,activityId,productId)==null){
                        seckillProductService.updateStock(productId, stock - 1);
                        SeckillOrder seckillOrder = new SeckillOrder();
                        seckillOrder.setActivityId(activityId);
                        seckillOrder.setUserId(userId);
                        seckillOrder.setProductId(productId);
                        seckillOrderService.add(seckillOrder);
                        result.put("code", 200);
                        result.put("status","success");
                        result.put("orderId",seckillOrder.getId());
                    }

                }

            }
        }catch (Exception e){
            result.put("code", 500);
            result.put("status","fail");
            e.printStackTrace();
        }
        return result;
    }
}
