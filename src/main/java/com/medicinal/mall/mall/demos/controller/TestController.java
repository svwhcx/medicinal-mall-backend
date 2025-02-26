package com.medicinal.mall.mall.demos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 9:53
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String testHello(){
        return "this is test hello";
    }
}
