package com.cloud.blog.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterRestTemplateCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JwtRestTemplateConfig implements JwtAccessTokenConverterRestTemplateCustomizer {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void customize(RestTemplate template) {
        template = restTemplate;
    }
}
