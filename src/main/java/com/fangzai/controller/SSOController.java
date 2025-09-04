package com.fangzai.controller;

import com.fangzai.entity.Menu;
import com.fangzai.entity.User;
import com.fangzai.service.IMenuService;
import com.fangzai.service.IUserService;
import com.fangzai.utils.JwtUtil;
import com.fangzai.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跨系统认证控制器
 * 用于处理主系统与子系统之间的单点登录
 */
@RestController
@RequestMapping("/sso")
@CrossOrigin
public class SSOController {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IMenuService menuService;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 验证Token并返回用户信息
     * 用于子系统验证用户身份
     */
    @PostMapping("/validateToken")
    public Result validateToken(@RequestParam String token) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            // 检查Redis中是否存在该token
            Object userIdFromRedis = redisTemplate.opsForValue().get("token:" + token);
            if (userIdFromRedis == null || !userId.equals(Long.valueOf(userIdFromRedis.toString()))) {
                return Result.fail("Token无效或已过期");
            }
            
            if (jwtUtil.validateToken(token, username)) {
                User user = userService.findByUsername(username);
                if (user != null && user.getStatus() == 1 && user.getDeleted() == 0) {
                    // 隐藏敏感信息
                    user.setPassword(null);
                    
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("user", user);
                    userInfo.put("roles", userService.getUserRoles(userId));
                    userInfo.put("permissions", userService.getUserPermissions(userId));
                    
                    return Result.success(userInfo);
                }
            }
            
            return Result.fail("用户不存在或已被禁用");
        } catch (Exception e) {
            return Result.fail("Token验证失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取用户在指定子系统的菜单权限
     */
    @GetMapping("/userMenus/{systemCode}")
    public Result getUserMenusBySystem(@PathVariable String systemCode, @RequestParam String token) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            // 验证token
            Object userIdFromRedis = redisTemplate.opsForValue().get("token:" + token);
            if (userIdFromRedis == null || !userId.equals(Long.valueOf(userIdFromRedis.toString()))) {
                return Result.fail("Token无效或已过期");
            }
            
            if (jwtUtil.validateToken(token, username)) {
                // 获取用户在指定系统的菜单
                List<Menu> userMenus = menuService.getUserMenuTree(userId);
                
                // 过滤出指定系统的菜单
                List<Menu> systemMenus = userMenus.stream()
                    .filter(menu -> systemCode.equals(menu.getSystemCode()))
                    .collect(java.util.stream.Collectors.toList());
                
                return Result.success(systemMenus);
            }
            
            return Result.fail("Token验证失败");
        } catch (Exception e) {
            return Result.fail("获取菜单失败: " + e.getMessage());
        }
    }
    
    /**
     * 生成子系统跳转URL
     * 用于从主系统跳转到子系统时携带认证信息
     */
    @PostMapping("/generateRedirectUrl")
    public Result generateRedirectUrl(@RequestParam String systemCode, @RequestParam String token) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            // 验证token
            Object userIdFromRedis = redisTemplate.opsForValue().get("token:" + token);
            if (userIdFromRedis == null || !userId.equals(Long.valueOf(userIdFromRedis.toString()))) {
                return Result.fail("Token无效或已过期");
            }
            
            if (jwtUtil.validateToken(token, username)) {
                // 这里可以根据系统编码返回对应的跳转URL
                // 实际项目中可以从配置文件或数据库中获取
                Map<String, String> systemUrls = new HashMap<>();
                systemUrls.put("HR_SYSTEM", "http://hr.example.com");
                systemUrls.put("FINANCE_SYSTEM", "http://finance.example.com");
                systemUrls.put("PROJECT_SYSTEM", "http://project.example.com");
                
                String baseUrl = systemUrls.get(systemCode);
                if (baseUrl == null) {
                    return Result.fail("未知的系统编码");
                }
                
                // 构建包含token的跳转URL
                String redirectUrl = baseUrl + "?token=" + token + "&fromMainSystem=true";
                
                Map<String, Object> result = new HashMap<>();
                result.put("redirectUrl", redirectUrl);
                result.put("systemCode", systemCode);
                
                return Result.success(result);
            }
            
            return Result.fail("Token验证失败");
        } catch (Exception e) {
            return Result.fail("生成跳转URL失败: " + e.getMessage());
        }
    }
    
    /**
     * 检查用户是否有访问指定子系统的权限
     */
    @GetMapping("/checkSystemAccess/{systemCode}")
    public Result checkSystemAccess(@PathVariable String systemCode, @RequestParam String token) {
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            // 验证token
            Object userIdFromRedis = redisTemplate.opsForValue().get("token:" + token);
            if (userIdFromRedis == null || !userId.equals(Long.valueOf(userIdFromRedis.toString()))) {
                return Result.fail("Token无效或已过期");
            }
            
            if (jwtUtil.validateToken(token, username)) {
                List<String> permissions = userService.getUserPermissions(userId);
                
                // 检查是否有对应系统的访问权限
                String requiredPermission = "subsystem:" + systemCode.toLowerCase() + ":access";
                boolean hasAccess = permissions.contains(requiredPermission);
                
                Map<String, Object> result = new HashMap<>();
                result.put("hasAccess", hasAccess);
                result.put("systemCode", systemCode);
                
                return Result.success(result);
            }
            
            return Result.fail("Token验证失败");
        } catch (Exception e) {
            return Result.fail("权限检查失败: " + e.getMessage());
        }
    }
}