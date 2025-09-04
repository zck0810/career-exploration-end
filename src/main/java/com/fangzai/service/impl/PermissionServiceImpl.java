package com.fangzai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fangzai.entity.Permission;
import com.fangzai.mapper.PermissionMapper;
import com.fangzai.service.IPermissionService;
import org.springframework.stereotype.Service;

/**
 * 权限服务实现类
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
}