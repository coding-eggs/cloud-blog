package com.cloud.blog.auth.config.service;


import com.cloud.blog.auth.config.base.BaseSecurityRole;
import com.cloud.blog.auth.config.base.BaseSecurityUser;
import com.cloud.blog.data.mapper.BlogRoleMapper;
import com.cloud.blog.data.mapper.BlogUserMapper;
import com.cloud.blog.data.model.po.BlogUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BaseUserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Autowired
    private BlogRoleMapper blogRoleMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("请求授权用户：{}",s);
        BlogUser user =  blogUserMapper.selectUserByUsername(s);
        if (ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("can not find user , username: {}" + s);
        }
        BaseSecurityUser baseSecurityUser = new BaseSecurityUser();
        BeanUtils.copyProperties(user,baseSecurityUser);
        baseSecurityUser.setAuthorities(blogRoleMapper.selectRoleListByUserId(baseSecurityUser.getId()).stream().map(e->{
            try {
                return objectMapper.readValue(objectMapper.writeValueAsBytes(e), BaseSecurityRole.class);
            } catch (IOException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()));
        return baseSecurityUser;
    }

}
