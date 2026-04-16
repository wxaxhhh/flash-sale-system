package com.example.flashsalesystem.service;

import com.example.flashsalesystem.entity.SeckillOrder;
import com.example.flashsalesystem.mapper.SeckillOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @CacheEvict(value = "orders", key = "#order.userId + '-' + #order.activityId + '-' + #order.productId")
    public void add(SeckillOrder order) {
        seckillOrderMapper.insert(order);
    }

    @Cacheable(value = "orders", key = "#userId + '-' + '#activityId' + '-' + #productId")
    public SeckillOrder findByUserActivityProduct(Long userId, Long activityId, Long productId) {
        return seckillOrderMapper.findByUserActivityProduct(userId, activityId, productId);
    }

    public SeckillOrder findById(Long orderId) {
        return seckillOrderMapper.selectById(orderId);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public void update(SeckillOrder order) {
        seckillOrderMapper.updateById(order);
    }

    public List<SeckillOrder> findByUserId(Long userId) {
        return seckillOrderMapper.selectByUserId(userId);
    }

    public void updateStatus(Long id, Integer status) {
        SeckillOrder order = seckillOrderMapper.selectById(id);
        if (order != null) {
            order.setStatus(status);
            seckillOrderMapper.updateById(order);
        }
    }
}