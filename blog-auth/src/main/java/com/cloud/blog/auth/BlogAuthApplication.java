package com.cloud.blog.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.cloud.blog.auth","com.cloud.blog.data","com.cloud.blog.common"})
@MapperScan(basePackages = "com.cloud.blog.data")
public class BlogAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAuthApplication.class, args);
    }

}
