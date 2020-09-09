package com.cloud.blog.auth.config.jwt;

import com.cloud.blog.data.model.base.BaseSecurityUser;
import com.cloud.blog.data.model.constant.BlogConstant;
import com.cloud.blog.data.model.po.BlogUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtTokenEnhancer extends JwtAccessTokenConverter {

    @Autowired
    private ObjectMapper blogObjectMapper;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        BaseSecurityUser baseSecurityUser = (BaseSecurityUser) authentication.getPrincipal();
        baseSecurityUser.setCredential(null);
        defaultOAuth2AccessToken.getAdditionalInformation().put(BlogConstant.USER_INFO,baseSecurityUser);
        return super.enhance(defaultOAuth2AccessToken,authentication);
    }

    /**
     * 对信息得转换
     * @param value
     * @param map
     * @return
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        OAuth2AccessToken oAuth2AccessToken = super.extractAccessToken(value,map);
        convertData(oAuth2AccessToken,oAuth2AccessToken.getAdditionalInformation());
        return oAuth2AccessToken;
    }

    private void convertData(OAuth2AccessToken accessToken, Map<String,?> map){
        accessToken.getAdditionalInformation().put(BlogConstant.USER_INFO,convertUserData(map.get(BlogConstant.USER_INFO)));
    }

    private BlogUser convertUserData(Object map){
        BlogUser blogUser = new BlogUser();
        try {
            blogUser = blogObjectMapper.readValue(blogObjectMapper.writeValueAsString(map),BlogUser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return blogUser;
    }


}
