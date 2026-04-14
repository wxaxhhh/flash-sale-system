package com.example.flashsalesystem.controller;
import com.example.flashsalesystem.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;
    @PostMapping("/execute")
    public Map<String,Object> executeSeckill(@RequestBody Map<String,Object> params)throws InterruptedException{
        return seckillService.executeSeckill(params);
    }


}
