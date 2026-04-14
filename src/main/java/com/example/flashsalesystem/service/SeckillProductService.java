package com.example.flashsalesystem.service;

import com.example.flashsalesystem.entity.SeckillProduct;
import com.example.flashsalesystem.mapper.SeckillProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeckillProductService {

    @Autowired
    private SeckillProductMapper seckillProductMapper;

    @CacheEvict(value="sps", allEntries=true)
    public void add(SeckillProduct product) {
        seckillProductMapper.insert(product);
    }

    @Cacheable(value="sps")
    public SeckillProduct findById(Long id) {
        return seckillProductMapper.findById(id);
    }
    @Cacheable(value="sps")
    public List<SeckillProduct> findAll() {
        return seckillProductMapper.findAll();
    }
    @Cacheable(value="sps")
    public List<SeckillProduct> findByActivityId(Long activityId) {
        return seckillProductMapper.findByActivityId(activityId);
    }

    // ========== 你来写 ==========
    // public int updateStock(Long id, Integer stock) {
    //     // 调用 mapper 更新库存
    // }
    @CacheEvict(value="sps", allEntries=true)
    public int updateStock(Long id,Integer stock){
        return seckillProductMapper.updateStock(id,stock);
    }
}