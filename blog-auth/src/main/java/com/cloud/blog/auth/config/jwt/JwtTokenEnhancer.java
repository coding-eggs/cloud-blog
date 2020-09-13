package com.cloud.blog.auth.config.jwt;

import com.cloud.blog.auth.config.base.BaseSecurityUser;
import com.cloud.blog.data.model.constant.BlogConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
* 描述 JWT 增强器 ， 将用户信息写入JWT
* @author myk
*/
@Configuration
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        BaseSecurityUser securityUser = (BaseSecurityUser) authentication.getPrincipal();
        defaultOAuth2AccessToken.getAdditionalInformation().put(BlogConstant.ROLE_INFO,securityUser.getAuthorities());
        defaultOAuth2AccessToken.getAdditionalInformation().put(BlogConstant.USER_NAME,securityUser.getNickname());
        return defaultOAuth2AccessToken;
    }
}
