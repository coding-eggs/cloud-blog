package com.cloud.blog.data.model.po;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * blog_role
 * @author 
 */
@Data
public class BlogRole implements  Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;


    private static final long serialVersionUID = 1L;
}