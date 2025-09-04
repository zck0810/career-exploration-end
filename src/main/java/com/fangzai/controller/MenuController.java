package com.fangzai.controller;

import com.fangzai.entity.Menu;
import com.fangzai.service.IMenuService;
import com.fangzai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 */
@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {
    
    @Autowired
    private IMenuService menuService;
    
    /**
     * 获取用户菜单树
     */
    @GetMapping("/userMenuTree/{userId}")
    public Result getUserMenuTree(@PathVariable Long userId) {
        List<Menu> menuTree = menuService.getUserMenuTree(userId);
        return Result.success(menuTree);
    }
    
    /**
     * 获取所有菜单树
     */
    @GetMapping("/allMenuTree")
    public Result getAllMenuTree() {
        List<Menu> menuTree = menuService.getAllMenuTree();
        return Result.success(menuTree);
    }
    
    /**
     * 根据系统编码获取菜单
     */
    @GetMapping("/system/{systemCode}")
    public Result getMenusBySystemCode(@PathVariable String systemCode) {
        List<Menu> menus = menuService.getMenusBySystemCode(systemCode);
        return Result.success(menus);
    }
    
    /**
     * 新增菜单
     */
    @PostMapping("/save")
    public Result save(@RequestBody Menu menu) {
        boolean success = menuService.save(menu);
        return success ? Result.success("新增菜单成功") : Result.fail("新增菜单失败");
    }
    
    /**
     * 更新菜单
     */
    @PostMapping("/update")
    public Result update(@RequestBody Menu menu) {
        boolean success = menuService.updateById(menu);
        return success ? Result.success("更新菜单成功") : Result.fail("更新菜单失败");
    }
    
    /**
     * 删除菜单
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        if (menu == null) {
            return Result.fail("菜单不存在");
        }
        
        menu.setDeleted(1);
        boolean success = menuService.updateById(menu);
        return success ? Result.success("删除菜单成功") : Result.fail("删除菜单失败");
    }
    
    /**
     * 根据ID查询菜单
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        if (menu != null && menu.getDeleted() == 0) {
            return Result.success(menu);
        }
        return Result.fail("菜单不存在");
    }
}