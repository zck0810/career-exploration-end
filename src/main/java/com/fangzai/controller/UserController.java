package com.fangzai.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangzai.entity.User;
import com.fangzai.service.IUserService;
import com.fangzai.utils.QueryPageParam;
import com.fangzai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    
    @Autowired
    private IUserService userService;
    
    /**
     * 分页查询用户
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        
        if (query.getParam() != null && !query.getParam().isEmpty()) {
            HashMap param = query.getParam();
            if (param.containsKey("search")) {
                String searchValue = param.get("search").toString();
                queryWrapper.and(wrapper -> wrapper
                        .like("username", searchValue)
                        .or()
                        .like("real_name", searchValue)
                        .or()
                        .like("email", searchValue));
            }
        }
        
        Page<User> pageResult = userService.page(page, queryWrapper);
        return Result.success(pageResult);
    }
    
    /**
     * 获取所有用户
     */
    @GetMapping("/list")
    public Result list() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        List<User> users = userService.list(queryWrapper);
        return Result.success(users);
    }
    
    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null && user.getDeleted() == 0) {
            user.setPassword(null); // 不返回密码
            return Result.success(user);
        }
        return Result.fail("用户不存在");
    }
    
    /**
     * 新增用户
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        try {
            boolean success = userService.createUser(user);
            return success ? Result.success("新增用户成功") : Result.fail("新增用户失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 更新用户
     */
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        try {
            boolean success = userService.updateUser(user);
            return success ? Result.success("更新用户成功") : Result.fail("更新用户失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        try {
            boolean success = userService.deleteUser(id);
            return success ? Result.success("删除用户成功") : Result.fail("删除用户失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 重置密码
     */
    @PostMapping("/resetPassword/{id}")
    public Result resetPassword(@PathVariable Long id) {
        try {
            User user = userService.getById(id);
            if (user == null || user.getDeleted() == 1) {
                return Result.fail("用户不存在");
            }
            
            user.setPassword("123456"); // 默认密码
            boolean success = userService.updateUser(user);
            return success ? Result.success("重置密码成功，新密码为：123456") : Result.fail("重置密码失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}