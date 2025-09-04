package com.fangzai.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangzai.entity.Permission;
import com.fangzai.service.IPermissionService;
import com.fangzai.utils.QueryPageParam;
import com.fangzai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * 权限管理控制器
 */
@RestController
@RequestMapping("/permission")
@CrossOrigin
public class PermissionController {
    
    @Autowired
    private IPermissionService permissionService;
    
    /**
     * 分页查询权限
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<Permission> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        
        if (query.getParam() != null && !query.getParam().isEmpty()) {
            HashMap param = query.getParam();
            if (param.containsKey("search")) {
                String searchValue = param.get("search").toString();
                queryWrapper.and(wrapper -> wrapper
                        .like("permission_name", searchValue)
                        .or()
                        .like("permission_code", searchValue)
                        .or()
                        .like("description", searchValue));
            }
        }
        
        Page<Permission> pageResult = permissionService.page(page, queryWrapper);
        return Result.success(pageResult);
    }
    
    /**
     * 获取所有权限
     */
    @GetMapping("/list")
    public Result list() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        List<Permission> permissions = permissionService.list(queryWrapper);
        return Result.success(permissions);
    }
    
    /**
     * 根据ID查询权限
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        if (permission != null && permission.getDeleted() == 0) {
            return Result.success(permission);
        }
        return Result.fail("权限不存在");
    }
    
    /**
     * 新增权限
     */
    @PostMapping("/save")
    public Result save(@RequestBody Permission permission) {
        try {
            permission.setCreateTime(LocalDateTime.now());
            permission.setUpdateTime(LocalDateTime.now());
            permission.setDeleted(0);
            boolean success = permissionService.save(permission);
            return success ? Result.success("新增权限成功") : Result.fail("新增权限失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 更新权限
     */
    @PostMapping("/update")
    public Result update(@RequestBody Permission permission) {
        try {
            permission.setUpdateTime(LocalDateTime.now());
            boolean success = permissionService.updateById(permission);
            return success ? Result.success("更新权限成功") : Result.fail("更新权限失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 删除权限
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        try {
            Permission permission = permissionService.getById(id);
            if (permission == null) {
                return Result.fail("权限不存在");
            }
            
            permission.setDeleted(1);
            permission.setUpdateTime(LocalDateTime.now());
            boolean success = permissionService.updateById(permission);
            return success ? Result.success("删除权限成功") : Result.fail("删除权限失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}