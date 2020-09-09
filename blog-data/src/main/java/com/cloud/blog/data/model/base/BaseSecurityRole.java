package com.cloud.blog.data.model.base;

import com.cloud.blog.data.model.po.BlogRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

//@Data
//@EqualsAndHashCode(callSuper = true)
//@Accessors(chain = true)
//@ApiModel(value = "com.cloud.blog.data.model.base.BaseSecurityRole",description = "oauth权限")
public class BaseSecurityRole extends BlogRole implements GrantedAuthority {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @Override
    public String getAuthority() {
        return null;
    }
}
