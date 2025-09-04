package com.fangzai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fangzai.entity.Menu;
import com.fangzai.mapper.MenuMapper;
import com.fangzai.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    
    @Autowired
    private MenuMapper menuMapper;
    
    @Override
    public List<Menu> getUserMenuTree(Long userId) {
        List<Menu> userMenus = menuMapper.getUserMenus(userId);
        return buildMenuTree(userMenus);
    }
    
    @Override
    public List<Menu> getAllMenuTree() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0)
                   .eq("status", 1)
                   .orderByAsc("sort");
        List<Menu> allMenus = list(queryWrapper);
        return buildMenuTree(allMenus);
    }
    
    @Override
    public List<Menu> getMenusBySystemCode(String systemCode) {
        return menuMapper.getMenusBySystemCode(systemCode);
    }
    
    @Override
    public List<Menu> buildMenuTree(List<Menu> menus) {
        if (menus == null || menus.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 获取根节点
        List<Menu> rootMenus = menus.stream()
                .filter(menu -> menu.getParentId() == null || menu.getParentId() == 0)
                .collect(Collectors.toList());
        
        // 递归构建树结构
        buildChildren(rootMenus, menus);
        
        return rootMenus;
    }
    
    /**
     * 递归构建子菜单
     */
    private void buildChildren(List<Menu> parentMenus, List<Menu> allMenus) {
        for (Menu parentMenu : parentMenus) {
            List<Menu> children = allMenus.stream()
                    .filter(menu -> parentMenu.getId().equals(menu.getParentId()))
                    .collect(Collectors.toList());
            
            if (!children.isEmpty()) {
                parentMenu.setChildren(children);
                buildChildren(children, allMenus);
            }
        }
    }
}