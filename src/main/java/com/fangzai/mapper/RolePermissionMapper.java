package com.fangzai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fangzai.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联Mapper
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}