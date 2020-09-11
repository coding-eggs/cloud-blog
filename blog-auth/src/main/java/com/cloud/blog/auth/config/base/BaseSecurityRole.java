package com.cloud.blog.auth.config.base;

import com.cloud.blog.data.model.po.BlogRole;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "com.cloud.blog.auth.config.base.BaseSecurityRole",description = "oauth权限")
public class BaseSecurityRole extends BlogRole implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return super.getRoleName();
    }
}
