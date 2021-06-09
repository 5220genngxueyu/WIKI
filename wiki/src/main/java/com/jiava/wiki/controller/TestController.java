package com.jiava.wiki.controller;

import com.jiava.wiki.domain.Test;
import com.jiava.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Resource
    private TestService testService;

    @Value("${test.hello:TEST}")
    private String testHello;
    //支持所有请求方式
    //只支持get方法的写法
    // @RequestMapping(value = "/hello",method = RequestMethod.GET)
    //@RequestMapping( "/hello")
    @GetMapping("/hello")
    public String hello(){
        return "hello world!"+testHello;
    }
    @PostMapping("/hello/post")
    public String hello(String name){
        return "hello world! Post."+name;
    }
    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }
}
