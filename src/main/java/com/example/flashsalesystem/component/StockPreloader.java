package com.example.flashsalesystem.component;

import com.example.flashsalesystem.entity.SeckillProduct;
import com.example.flashsalesystem.service.SeckillOrderService;
import com.example.flashsalesystem.service.SeckillProductService;
import com.example.flashsalesystem.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class StockPreloader implements ApplicationRunner {
    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    public void run(ApplicationArguments args) throws Exception {
        List<SeckillProduct> products=seckillProductService.findAll();
        for(SeckillProduct product:products){
            redisTemplate.opsForValue().set("product_stock:"+product.getId(),String.valueOf(product.getStock()));
        }

    }

}
