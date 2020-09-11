package com.cloud.blog.gateway.service.feign;

import com.cloud.blog.data.model.base.BaseJwt;
import com.cloud.blog.gateway.service.feign.fallback.OAuth2HystrixFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-server" ,fallbackFactory = OAuth2HystrixFallBack.class)
public interface OAuth2FeignClient {

    @PostMapping(value = "/auth/server/oauth/token",headers = {HttpHeaders.CONTENT_TYPE,MediaType.MULTIPART_FORM_DATA_VALUE})
    BaseJwt getJwtToken(@RequestBody MultiValueMap<String,String> map);


}
