package com.cloud.blog.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@SpringBootTest
class BlogGatewayApplicationTests {

    @Autowired
    private RestTemplate restTemplate;


    @Test
    void contextLoads() {

    }

}
