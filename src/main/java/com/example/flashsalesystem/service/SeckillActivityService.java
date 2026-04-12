package com.example.flashsalesystem.service;

import com.example.flashsalesystem.entity.SeckillActivity;
import com.example.flashsalesystem.mapper.SeckillActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillActivityService {
    @Autowired
    SeckillActivityMapper seckillActivityMapper;

    public void add(SeckillActivity seckillActivity) {
        seckillActivityMapper.insert(seckillActivity);
    }

    public SeckillActivity findById(Long id) {
        return seckillActivityMapper.findById(id);
    }

    public List<SeckillActivity> findAll() {
        return seckillActivityMapper.findAll();
    }
}
