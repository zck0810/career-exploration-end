package com.fangzai.controller;

import com.fangzai.dto.LoginRequest;
import com.fangzai.dto.LoginResponse;
import com.fangzai.entity.User;
import com.fangzai.service.IUserService;
import com.fangzai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    
    @Autowired
    private IUserService userService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            User user = userService.findByUsername(loginRequest.getUsername());
            
            List<String> roles = userService.getUserRoles(user.getId());
            List<String> permissions = userService.getUserPermissions(user.getId());
            
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUserId(user.getId());
            response.setUsername(user.getUsername());
            response.setRealName(user.getRealName());
            response.setRoles(roles);
            response.setPermissions(permissions);
            
            return Result.success(response);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
    
    /**
     * 用户注销
     */
    @PostMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        // TODO: 将token加入黑名单或从Redis中删除
        return Result.success("注销成功");
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/userinfo")
    public Result getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            // TODO: 从token中解析用户信息
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }
}