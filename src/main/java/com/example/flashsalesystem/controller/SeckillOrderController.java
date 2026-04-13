package com.example.flashsalesystem.controller;


import com.example.flashsalesystem.entity.SeckillOrder;
import com.example.flashsalesystem.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @PostMapping("/add")
    public Map<String,Object> addOrder(@RequestBody SeckillOrder seckillOrder)
    {
        Map<String,Object> result = new HashMap<>();
        try{
            seckillOrderService.add(seckillOrder);
            result.put("code",200);
            result.put("msg","success");

        }catch (Exception e){
            result.put("code",500);
            result.put("msg",e.getMessage());
        }return  result;
    }

    @GetMapping("/{id}")
    public SeckillOrder  getOrderById(@PathVariable Long id){
        return seckillOrderService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<SeckillOrder> getOrdersByUser(@PathVariable Long userId){
        return seckillOrderService.findByUserId(userId);
    }

    @PutMapping("/status/{id}")
    public Map<String,Object> updateOrderStatus(@PathVariable Long id, @RequestParam Integer status ) {
        Map<String, Object> result = new HashMap<>();
        try {

            seckillOrderService.updateStatus(id, status);
            result.put("code",200);
            result.put("msg","success");
        }catch (Exception e){
            result.put("code",500);
            result.put("msg",e.getMessage());
        }
        return result;
    }





}
