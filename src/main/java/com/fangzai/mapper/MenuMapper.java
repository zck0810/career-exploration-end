package com.fangzai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fangzai.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单Mapper
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    
    /**
     * 根据用户ID查询用户菜单
     */
    @Select("SELECT DISTINCT m.* FROM sys_menu m " +
            "INNER JOIN sys_role_menu rm ON m.id = rm.menu_id " +
            "INNER JOIN sys_user_role ur ON rm.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND m.deleted = 0 AND m.status = 1 " +
            "ORDER BY m.sort ASC")
    List<Menu> getUserMenus(@Param("userId") Long userId);
    
    /**
     * 根据父菜单ID查询子菜单
     */
    @Select("SELECT * FROM sys_menu WHERE parent_id = #{parentId} AND deleted = 0 AND status = 1 ORDER BY sort ASC")
    List<Menu> getMenusByParentId(@Param("parentId") Long parentId);
    
    /**
     * 根据系统编码查询菜单
     */
    @Select("SELECT * FROM sys_menu WHERE system_code = #{systemCode} AND deleted = 0 AND status = 1 ORDER BY sort ASC")
    List<Menu> getMenusBySystemCode(@Param("systemCode") String systemCode);
}