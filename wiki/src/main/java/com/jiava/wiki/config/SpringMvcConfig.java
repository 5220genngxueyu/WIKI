//package com.jiava.wiki.config;
//
//import com.jiava.wiki.interceptor.LogInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.annotation.Resource;
//
//@Configuration
//public class SpringMvcConfig implements WebMvcConfigurer {
//
//    @Resource
//    LogInterceptor logInterceptor;
//
//
//    public void addInterceptors(InterceptorRegistry registry){
//
//        registry.addInterceptor(logInterceptor)
//        .excludePathPatterns("/login").addPathPatterns("/**");
//
//    }
//}
