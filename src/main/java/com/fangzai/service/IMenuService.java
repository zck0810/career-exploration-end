package com.fangzai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fangzai.entity.Menu;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface IMenuService extends IService<Menu> {
    
    /**
     * 获取用户菜单树
     */
    List<Menu> getUserMenuTree(Long userId);
    
    /**
     * 获取所有菜单树
     */
    List<Menu> getAllMenuTree();
    
    /**
     * 根据系统编码获取菜单
     */
    List<Menu> getMenusBySystemCode(String systemCode);
    
    /**
     * 构建菜单树
     */
    List<Menu> buildMenuTree(List<Menu> menus);
}