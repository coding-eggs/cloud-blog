package com.cloud.blog.auth.config.service;


import com.cloud.blog.data.model.base.BaseSecurityRole;
import com.cloud.blog.data.model.base.BaseSecurityUser;
import com.cloud.blog.data.mapper.BlogRoleMapper;
import com.cloud.blog.data.mapper.BlogUserMapper;
import com.cloud.blog.data.model.po.BlogUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@Slf4j
@Service
public class BaseUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Autowired
    private BlogRoleMapper blogRoleMapper;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("请求授权用户：{}",s);
        BaseSecurityUser user =  blogUserMapper.selectUserByUsername(s);
        if (ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("can not find user , username: {}" + s);
        }
        user.setAuthorities(blogRoleMapper.selectRoleListByUserId(user.getId()));
        return user;
    }

}
