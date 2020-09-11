package com.cloud.blog.auth.config.oauth;


import com.cloud.blog.auth.config.jwt.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerTokenServicesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
* 这里import {@link AuthorizationServerTokenServicesConfiguration} 类 ，该类是Spring Security OAuth2 自有得配置类
* 起初我得配置都自定义，但是产生了一个问题，就是在gateway server 服务访问/oauth/token_key 得时候会401， 产生得原因，
* 是因为convert.isPublic() 方法返回false 直接抛出异常， 经过反复查看源码发现，covert并不是我们自定义的那个，而是一个新的
* 并没有对 keypair 进行赋值。所以这里我们使用原生得配置类，直接加载进来，把需要自定义得tokenService 自定义
* @author myk
*/
@Configuration
@EnableAuthorizationServer
@Import({AuthorizationServerProperties.class,AuthorizationServerTokenServicesConfiguration.class})
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
    * 这里idea 会爆红，显示让加入qualifier注解 来声明注入哪个类，实际上不用得，且只有一个tokenStore得实例，
    * 在{@link AuthorizationServerTokenServicesConfiguration} 内得 {@link AuthorizationServerTokenServicesConfiguration.JwtKeyStoreConfiguration}
     *  上有@Conditional(JwtKeyStoreCondition.class)，从 {@link AuthorizationServerTokenServicesConfiguration.JwtKeyStoreCondition} 中可以看出，此类需要
     *  在application.yaml 文件配置security.oauth2.authorization.jwt 开头得三个参数才能想IOC中注入这个实例，所以也只有 KeyStore这个配置可以生效。这里爆红是编辑器得错误。
    *
    */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    public UserDetailsService baseUserDetailsServiceImpl;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;


    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 开启 /oauth/token_key 验证端口无权限访问
        security.tokenKeyAccess("permitAll()")
                // 开启 /oauth/check_token 验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()")
                // 允许表单验证
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用jdbc 客户端详情服务
        clients.withClientDetails(jdbcClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenServices(defaultTokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST,HttpMethod.OPTIONS);
    }


    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return DefaultTokenServices
     */

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices(){
        List<TokenEnhancer> tokenEnhancerList = new ArrayList<>();
        tokenEnhancerList.add(jwtTokenEnhancer);
        tokenEnhancerList.add(jwtAccessTokenConverter);
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(tokenEnhancerList);
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setAuthenticationManager(authenticationManager);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setTokenEnhancer(enhancerChain);
        tokenServices.setClientDetailsService(jdbcClientDetailsService());
        // 支持使用refresh token 刷新 access token
        tokenServices.setSupportRefreshToken(true);
        // 允许重复使用refresh token
        tokenServices.setReuseRefreshToken(true);
        return tokenServices;
    }
}
