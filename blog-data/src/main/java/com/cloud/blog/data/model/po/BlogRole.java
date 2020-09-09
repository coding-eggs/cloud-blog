package com.cloud.blog.data.model.po;

import java.io.Serializable;
import lombok.Data;

/**
 * blog_role
 * @author 
 */
@Data
public class BlogRole implements  Serializable {
    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色描述
     */
    private String roleName;

    private static final long serialVersionUID = 1L;
}