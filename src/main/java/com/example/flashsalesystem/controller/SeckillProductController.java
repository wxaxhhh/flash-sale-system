package com.example.flashsalesystem.controller;

import com.example.flashsalesystem.entity.SeckillProduct;
import com.example.flashsalesystem.service.SeckillProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class SeckillProductController {
    @Autowired
    private SeckillProductService seckillProductService;

    @PostMapping("/add")
    public Map<String,Object> addProduct(@RequestBody SeckillProduct seckillProduct){
        Map<String,Object> result = new HashMap<>();
        try{
            seckillProductService.add(seckillProduct);
            result.put("code",200);
            result.put("msg","success");
        }catch (Exception e){
            result.put("code",500);
            result.put("msg","fail");
        }
        return result;
    }

    @GetMapping("/{id}")
    public SeckillProduct getProductById(@PathVariable("id") Long id){
        return seckillProductService.findById(id);
    }

    @GetMapping("/list")
    public List<SeckillProduct> listProduct(){
        return seckillProductService.findAll();
    }

    @GetMapping("/activity/{activityId}")
    public List<SeckillProduct> getProductsByActivity(@PathVariable Long activityId)
    {
        return seckillProductService.findByActivityId(activityId);
    }

    @PutMapping("/stock/{id}")
    public int updateStock(@PathVariable Long id,@RequestParam Integer stock){
        return seckillProductService.updateStock(id, stock);
    }

}
