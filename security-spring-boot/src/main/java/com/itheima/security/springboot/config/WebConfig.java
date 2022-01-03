package com.itheima.security.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration//就相当于springmvc.xml文件
public class WebConfig implements WebMvcConfigurer {


    //默认Url根路径跳转到/login，此url为spring security提供
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("redirect:/login");

        registry.addViewController("/").setViewName("redirect:/login-view");
        //指定我们自己的登录页,spring security以重定向方式跳转到/login-view
        registry.addViewController("/login-view").setViewName("login");

    }

}
