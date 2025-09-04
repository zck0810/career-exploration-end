package com.fangzai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fangzai.entity.Role;
import com.fangzai.mapper.RoleMapper;
import com.fangzai.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
}