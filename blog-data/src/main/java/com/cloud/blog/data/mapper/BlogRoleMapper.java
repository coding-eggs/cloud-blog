package com.cloud.blog.data.mapper;

import com.cloud.blog.data.model.base.BaseSecurityRole;
import com.cloud.blog.data.model.po.BlogRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogRole record);

    int insertSelective(BlogRole record);

    BlogRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogRole record);

    int updateByPrimaryKey(BlogRole record);

    List<BaseSecurityRole> selectRoleListByUserId(@Param("userId") Long userId);
}