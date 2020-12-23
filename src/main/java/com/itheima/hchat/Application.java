package com.itheima.hchat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.itheima.hchat")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
