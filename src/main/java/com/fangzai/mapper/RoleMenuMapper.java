package com.fangzai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fangzai.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色菜单关联Mapper
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
}