package com.cloud.blog.gateway.service.feign.fallback;

import com.cloud.blog.gateway.service.feign.OAuth2FeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OAuth2HystrixFallBack implements FallbackFactory<OAuth2FeignClient> {

    @Override
    public OAuth2FeignClient create(Throwable throwable) {
        return map -> {
            log.error(throwable.getMessage());
            return null;
        };
    }
}
