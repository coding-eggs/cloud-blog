package com.cloud.blog.gateway.config.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.oauth2.client")
public class OAuth2ClientProperties {

    private String accessTokenUri;

    private String userAuthorizationUri;

    private String clientId;

    private String clientSecret;

    private String registeredRedirectUri;

    private String grantType;

}
