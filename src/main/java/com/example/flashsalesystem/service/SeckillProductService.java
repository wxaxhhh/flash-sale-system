package com.example.flashsalesystem.service;

import com.example.flashsalesystem.entity.SeckillProduct;
import com.example.flashsalesystem.mapper.SeckillProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeckillProductService {

    @Autowired
    private SeckillProductMapper seckillProductMapper;

    public void add(SeckillProduct product) {
        seckillProductMapper.insert(product);
    }

    public SeckillProduct findById(Long id) {
        return seckillProductMapper.findById(id);
    }

    public List<SeckillProduct> findAll() {
        return seckillProductMapper.findAll();
    }

    public List<SeckillProduct> findByActivityId(Long activityId) {
        return seckillProductMapper.findByActivityId(activityId);
    }

    // ========== 你来写 ==========
    // public int updateStock(Long id, Integer stock) {
    //     // 调用 mapper 更新库存
    // }
    public int updateStock(Long id,Integer stock){
        return seckillProductMapper.updateStock(id,stock);
    }
}