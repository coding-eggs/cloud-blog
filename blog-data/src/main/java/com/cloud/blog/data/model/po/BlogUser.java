package com.cloud.blog.data.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * blog_user
 * @author 
 */
@Getter
@Setter
public class BlogUser implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 登录类型 默认登录类型 为10 （本系统登录）20（qq登录） 30 （微信登录）40（手机登录）
50（邮箱登录）
     */
    @ApiModelProperty(value = "认证类型")
    private Integer identifyType;

    /**
     * 认证凭证
     */
    @ApiModelProperty(value = "凭证")
    private String credential;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private LocalDateTime birthday;

    /**
     * 头像url 
     */
    @ApiModelProperty(value = "头像")
    private String figureUrl;

    /**
     * 性别  1 男  2 女  3其他
     */
    @ApiModelProperty(value = "性别")
    private Byte sex;

    /**
     * 电话
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String mail;

    /**
     * 上次登录时间
     */
    @ApiModelProperty(value = "上次登录得时间")
    private LocalDateTime lastLoginTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

    /**
     * 上一次得修改密码得时间
     */
    @ApiModelProperty(value = "上次修改密码得时间")
    private LocalDateTime lastPasswordResetTime;

    /**
     * 是否锁定 默认0 不锁定
     */
    @ApiModelProperty(value = "锁定标识")
    private Byte locked;

    /**
     * 是否删除 0未删除
     */
    @ApiModelProperty(value = "删除表示")
    private Byte deleted;

    private static final long serialVersionUID = 1L;
}