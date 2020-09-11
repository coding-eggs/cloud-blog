package com.cloud.blog.auth.controller;


import com.cloud.blog.data.model.base.BaseResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class OAuth2LogoutController {


    @GetMapping("/oauth/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        return new BaseResponse<>(true);
    }

}
