package com.cloud.blog.gateway.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterRestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;


/**
* 在部署得时候 获取公钥得地址考虑到高可用，所以得采用负载均衡，利用nacos。通过看源码知道，请求公钥是restTemplate来完成得，这个template没有
 * 加LoadBalanced注解，所以没有负载均衡得功能，阅读源码知道，初始化时会扫描 {@link JwtAccessTokenConverterRestTemplateCustomizer}这个类
 * 然后指定对restTemplate得配置，默认时没有得，这里我们实现一个这个类，从源码中我们得知LoadBalanced实现负载其实就是一个拦截器，这里我们生创建一个拦截器
 * 放进这个restTemplate。 其实还有第二种方案就是重写jwtTokenStore，但是比较麻烦。
* @author myk
*/
@Configuration
public class JwtRestTemplateConfig implements JwtAccessTokenConverterRestTemplateCustomizer {


    @Autowired
    private LoadBalancerRequestFactory requestFactory;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @Override
    public void customize(RestTemplate template) {
        template.setInterceptors(new ArrayList<>(Collections.singletonList(new LoadBalancerInterceptor(loadBalancerClient, requestFactory))));
    }
}
