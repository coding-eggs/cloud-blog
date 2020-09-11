package com.cloud.blog.auth.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService baseUserDetailsServiceImpl;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Primary
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(baseUserDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/security/login","/oauth/**","/actuator/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/security/login").permitAll()
                .antMatchers("/js/**","/html/**","/img/**","/font/**","/css/**","/layer/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/html/login.html")
                .loginProcessingUrl("/security/login")
                .and()
                .logout()
                .logoutSuccessUrl("/oauth/logout")
                .and().headers().frameOptions().sameOrigin();


    }





}
