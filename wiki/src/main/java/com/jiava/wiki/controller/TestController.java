package com.jiava.wiki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    //支持所有请求方式
    //只支持get方法的写法
    // @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @RequestMapping( "/hello")
    public String hello(){
        return "hello world";
    }
}
