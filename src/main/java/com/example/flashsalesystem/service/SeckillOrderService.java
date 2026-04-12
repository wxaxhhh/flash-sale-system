package com.example.flashsalesystem.service;

import com.example.flashsalesystem.entity.SeckillOrder;
import com.example.flashsalesystem.mapper.SeckillOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    public void add(SeckillOrder order) {
        seckillOrderMapper.insert(order);
    }

    public SeckillOrder findById(Long id) {
        return seckillOrderMapper.findById(id);
    }

    public List<SeckillOrder> findByUserId(Long userId) {
        return seckillOrderMapper.findByUserId(userId);
    }

    public int updateStatus(Long id, Integer status) {
        return seckillOrderMapper.updateStatus(id, status);
    }

    // ========== 你来写 ==========
    // public SeckillOrder findByUserActivityProduct(Long userId, Long activityId, Long productId) {
    //     // 调用 mapper 查询，防止重复秒杀
    // }
    public SeckillOrder findByUserActivityProduct(Long userId,Long activityId,Long productId) {
        return seckillOrderMapper.findByUserActivityProduct(userId,activityId,productId);
    }
}