package com.cloud.blog.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@SpringBootTest
class BlogGatewayApplicationTests {

    @Test
    void contextLoads() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        System.out.println((String) new RestTemplate()
                .exchange("http://127.0.0.1:9527/auth/server/oauth/token_key", HttpMethod.GET,request, Map.class).getBody()
                .get("value"));

    }

}
