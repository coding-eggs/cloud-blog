package com.cloud.blog.auth.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
public class JwtTokenConfig {


    @Autowired
    private AuthorizationServerProperties serverProperties;



    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        final JwtAccessTokenConverter converter = new JwtTokenEnhancer();
        converter.setKeyPair(keyPair());
        return converter;
    }

    @Bean
    @Primary
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        AuthorizationServerProperties.Jwt jwt = serverProperties.getJwt();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(jwt.getKeyStore()), jwt.getKeyStorePassword().toCharArray());
        return keyStoreKeyFactory.getKeyPair(jwt.getKeyAlias(), jwt.getKeyStorePassword().toCharArray());
    }



}
