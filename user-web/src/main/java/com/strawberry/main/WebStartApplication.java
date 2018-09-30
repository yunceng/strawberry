package com.strawberry.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 应用的启动类
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.27
 */

//声明该项目是一个springboot项目
@SpringBootApplication
//扫描controller包，使controller注解生效
@ComponentScan(value = {"com.strawberry.controller", "com.strawberry.dao", "com.strawberry.service.impl",
        "com.strawberry.conf"})
//扫描dao包
@MapperScan("com.strawberry.dao")
//扫描filter包，是过滤器生效
@ServletComponentScan(value = "com.strawberry.filter")
public class WebStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebStartApplication.class, args);
    }

}
