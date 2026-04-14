package com.example.flashsalesystem.service;

import com.example.flashsalesystem.entity.SeckillActivity;
import com.example.flashsalesystem.mapper.SeckillActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillActivityService {
    @Autowired
    SeckillActivityMapper seckillActivityMapper;

    @CacheEvict(value="sas",allEntries=true)
    public void add(SeckillActivity seckillActivity) {
        seckillActivityMapper.insert(seckillActivity);
    }

    @Cacheable(value="sas")
    public SeckillActivity findById(Long id) {
        return seckillActivityMapper.findById(id);
    }

    @Cacheable(value="sas")
    public List<SeckillActivity> findAll() {
        return seckillActivityMapper.findAll();
    }
}
