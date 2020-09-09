package com.cloud.blog.data.mapper;

import com.cloud.blog.data.model.base.BaseSecurityUser;
import com.cloud.blog.data.model.po.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogUser record);

    int insertSelective(BlogUser record);

    BlogUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogUser record);

    int updateByPrimaryKey(BlogUser record);

    BaseSecurityUser selectUserByUsername(@Param("username") String username);
}