package com.example.flashsalesystem;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private MQProducer mqProducer;

    @GetMapping("/test")
    public String test() {
        mqProducer.send("test-topic", "hello rocketmq");
        return "ok";
    }
}