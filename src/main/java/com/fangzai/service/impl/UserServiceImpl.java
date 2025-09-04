package com.fangzai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fangzai.entity.User;
import com.fangzai.mapper.UserMapper;
import com.fangzai.service.IUserService;
import com.fangzai.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public String login(String username, String password) {
        User user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        System.out.println("Login attempt - Username: " + username + ", Password: " + password);
        System.out.println("Stored hash: " + user.getPassword());
        
        if (user.getStatus() == 0) {
            throw new RuntimeException("用户已被禁用");
        }
        
        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Password match result: " + passwordMatch);
        
        if (!passwordMatch) {
            throw new RuntimeException("密码错误");
        }
        
        // 生成JWT Token
        String token = jwtUtil.generateToken(username, user.getId());
        
        // 将用户信息存入Redis，有效期24小时 (暂时禁用Redis for testing)
        try {
            redisTemplate.opsForValue().set("user:" + user.getId(), user, 24, TimeUnit.HOURS);
            redisTemplate.opsForValue().set("token:" + token, user.getId(), 24, TimeUnit.HOURS);
        } catch (Exception e) {
            // Redis not available, continue without caching
            System.out.println("Redis not available: " + e.getMessage());
        }
        
        return token;
    }
    
    @Override
    public List<String> getUserRoles(Long userId) {
        return userMapper.getUserRoles(userId);
    }
    
    @Override
    public List<String> getUserPermissions(Long userId) {
        return userMapper.getUserPermissions(userId);
    }
    
    @Override
    public boolean createUser(User user) {
        // 检查用户名是否已存在
        if (findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setDeleted(0);
        
        return save(user);
    }
    
    @Override
    public boolean updateUser(User user) {
        User existingUser = getById(user.getId());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 如果密码有变化，需要重新加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }
        
        user.setUpdateTime(LocalDateTime.now());
        return updateById(user);
    }
    
    @Override
    public boolean deleteUser(Long userId) {
        User user = getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        user.setDeleted(1);
        user.setUpdateTime(LocalDateTime.now());
        return updateById(user);
    }
}