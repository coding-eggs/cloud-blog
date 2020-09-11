package com.cloud.blog.gateway.controller;

import com.cloud.blog.data.model.base.BaseJwt;
import com.cloud.blog.data.model.base.BaseResponse;
import com.cloud.blog.gateway.config.oauth.OAuth2ClientProperties;
import com.cloud.blog.gateway.service.feign.OAuth2FeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
* 描述 回调地址
* @author myk
*/

@RestController
public class OAuth2Controller {

    @Autowired
    private OAuth2ClientProperties properties;

    @Autowired
    private OAuth2FeignClient feignClient;


    @GetMapping(value = "/oauth/callback")
    public BaseResponse<BaseJwt> authCodeSuccessCallBack(String code){
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("client_id",properties.getClientId());
        map.add("client_secret",properties.getClientSecret());
        map.add("redirect_uri",properties.getRegisteredRedirectUri());
        map.add("grant_type",properties.getGrantType());
        map.add("code",code);
        BaseJwt jwt = feignClient.getJwtToken(map);
        return new BaseResponse<>(jwt);
    }


    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";
    }
}
