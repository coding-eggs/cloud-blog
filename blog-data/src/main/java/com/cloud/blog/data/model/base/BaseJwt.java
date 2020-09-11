package com.cloud.blog.data.model.base;

import com.cloud.blog.data.model.po.BlogRole;
import com.cloud.blog.data.model.po.BlogUser;
import lombok.Data;

import java.util.List;

/**
 * @ClassName JWT
 * @Description jwt 实体类
 * @Author wsail
 * @Date 2019/12/25 16:20
 **/

@Data
public class BaseJwt {

    /** access token */
    private String access_token;

    /** token 类型 */
    private String token_type;

    /** refresh token */
    private String refresh_token;

    /** 用户信息 */
    private String username;

    /** 角色信息 */
    private List<BlogRole> role_info;

    /** 过期时间 */
    private int expires_in;

    /** 作用域 */
    private String scope;

    /** jti */
    private String jti;

}
