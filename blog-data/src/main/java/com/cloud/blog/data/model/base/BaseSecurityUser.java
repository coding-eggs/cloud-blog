package com.cloud.blog.data.model.base;

import com.cloud.blog.data.model.enums.EnumDelete;
import com.cloud.blog.data.model.enums.EnumLock;
import com.cloud.blog.data.model.po.BlogUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;



@Getter
@Setter
@ApiModel(value = "com.cloud.blog.data.model.base.BaseSecurityUser",description = "安全类")
public class BaseSecurityUser extends BlogUser implements UserDetails, Serializable {

    @ApiModelProperty(value = "角色列表")
    private List<BaseSecurityRole> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getCredential();
    }
    @Override
    public boolean isEnabled() {
        return EnumDelete.UNDELETE.getValue().equals(super.getDeleted());
    }
    @Override
    public boolean isAccountNonLocked() {
        return EnumLock.UNLOCK.getValue().equals(super.getLocked());
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

}
