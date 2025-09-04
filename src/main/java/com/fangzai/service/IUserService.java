package com.fangzai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fangzai.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface IUserService extends IService<User> {
    
    /**
     * 根据用户名查询用户
     */
    User findByUsername(String username);
    
    /**
     * 用户登录
     */
    String login(String username, String password);
    
    /**
     * 获取用户角色
     */
    List<String> getUserRoles(Long userId);
    
    /**
     * 获取用户权限
     */
    List<String> getUserPermissions(Long userId);
    
    /**
     * 创建用户
     */
    boolean createUser(User user);
    
    /**
     * 更新用户
     */
    boolean updateUser(User user);
    
    /**
     * 删除用户
     */
    boolean deleteUser(Long userId);
}