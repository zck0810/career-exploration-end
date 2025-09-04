package com.fangzai.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangzai.entity.Role;
import com.fangzai.service.IRoleService;
import com.fangzai.utils.QueryPageParam;
import com.fangzai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {
    
    @Autowired
    private IRoleService roleService;
    
    /**
     * 分页查询角色
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<Role> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        
        if (query.getParam() != null && !query.getParam().isEmpty()) {
            HashMap param = query.getParam();
            if (param.containsKey("search")) {
                String searchValue = param.get("search").toString();
                queryWrapper.and(wrapper -> wrapper
                        .like("role_name", searchValue)
                        .or()
                        .like("role_code", searchValue)
                        .or()
                        .like("description", searchValue));
            }
        }
        
        Page<Role> pageResult = roleService.page(page, queryWrapper);
        return Result.success(pageResult);
    }
    
    /**
     * 获取所有角色
     */
    @GetMapping("/list")
    public Result list() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        List<Role> roles = roleService.list(queryWrapper);
        return Result.success(roles);
    }
    
    /**
     * 根据ID查询角色
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        if (role != null && role.getDeleted() == 0) {
            return Result.success(role);
        }
        return Result.fail("角色不存在");
    }
    
    /**
     * 新增角色
     */
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        try {
            role.setCreateTime(LocalDateTime.now());
            role.setUpdateTime(LocalDateTime.now());
            role.setDeleted(0);
            boolean success = roleService.save(role);
            return success ? Result.success("新增角色成功") : Result.fail("新增角色失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 更新角色
     */
    @PostMapping("/update")
    public Result update(@RequestBody Role role) {
        try {
            role.setUpdateTime(LocalDateTime.now());
            boolean success = roleService.updateById(role);
            return success ? Result.success("更新角色成功") : Result.fail("更新角色失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        try {
            Role role = roleService.getById(id);
            if (role == null) {
                return Result.fail("角色不存在");
            }
            
            role.setDeleted(1);
            role.setUpdateTime(LocalDateTime.now());
            boolean success = roleService.updateById(role);
            return success ? Result.success("删除角色成功") : Result.fail("删除角色失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}