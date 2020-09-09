package com.cloud.blog.common.config.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Configuration
public class FeignRequestInterceptorConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        if(request != null){
            log.debug("调用feign 传递header 携带 token");

            //只携带token
//            String accessToken = request.getHeader(BlogConstant.AUTHORIZATION_HEADER);
//            requestTemplate.header(BlogConstant.AUTHORIZATION_HEADER,accessToken);
//            log.debug("access_token: {}",accessToken);

            // 携带全部得头
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames!= null &&headerNames.hasMoreElements()){
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                requestTemplate.header(name,values);
                log.debug("name: \t\t {}",name);
                log.debug("values: \t\t {}",values);
            }

        }
    }
}
