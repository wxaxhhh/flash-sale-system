package com.example.flashsalesystem.controller;

import com.example.flashsalesystem.entity.SeckillActivity;
import com.example.flashsalesystem.mapper.SeckillActivityMapper;
import com.example.flashsalesystem.service.SeckillActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity")
public class SeckillActivityController {
    @Autowired
    private SeckillActivityService seckillActivityService;


    @PostMapping("/add")
    public Map<String,Object> addActivity(@RequestBody SeckillActivity seckillActivity)
    {
        Map<String,Object> result=new HashMap<>();
        try{
        seckillActivityService.add(seckillActivity);
        result.put("code",200);
        result.put("msg","success");

        }catch(Exception e){
            result.put("code",500);
            result.put("msg","fail");
        }
        return result;
    }


    @GetMapping("/{id}")
    public  SeckillActivity getActivityById(@PathVariable Long id){
        return seckillActivityService.findById(id);
    }


    @GetMapping("/list")
    public List<SeckillActivity> listActivities(){
        return seckillActivityService.findAll();
    }


}





