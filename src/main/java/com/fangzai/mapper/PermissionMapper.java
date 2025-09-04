package com.fangzai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fangzai.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限Mapper
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}