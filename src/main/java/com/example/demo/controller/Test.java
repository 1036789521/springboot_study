package com.example.demo.controller;

import com.example.demo.entity.Test11Entity;
import com.example.demo.service.Test11Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* @Description: 简单的demo测试
*
* @Author wangyanlin
* @Date 14:14 2019/3/13
* @Param
* @return
**/
@RestController
@RequestMapping("/test")
public class Test {
    @Resource
    private Test11Service test11Service;

    @GetMapping(value = "/v1/getById")
    public Test11Entity  get(@RequestParam("id") String id){

        return test11Service.getById(id);

    }

}
