-- 内容管理系统初始数据

-- 插入默认用户 (admin/admin123)
INSERT IGNORE INTO `sys_user` (`id`, `username`, `password`, `real_name`, `email`, `status`, `create_time`, `update_time`, `deleted`) 
VALUES (1, 'admin', '$2a$10$7JB720yubVSan7bCG6Y.dOGb/rQAoX3eKqJxhIhOYG3v2NqhgV3RG', '系统管理员', 'admin@example.com', 1, NOW(), NOW(), 0);

-- 插入默认角色
INSERT IGNORE INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `status`, `create_time`, `update_time`, `deleted`) 
VALUES 
(1, '超级管理员', 'SUPER_ADMIN', '系统超级管理员，拥有所有权限', 1, NOW(), NOW(), 0),
(2, '系统管理员', 'SYSTEM_ADMIN', '系统管理员，拥有系统管理权限', 1, NOW(), NOW(), 0),
(3, '普通用户', 'USER', '普通用户，基础权限', 1, NOW(), NOW(), 0);

-- 插入菜单数据 
INSERT IGNORE INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `path`, `component`, `icon`, `sort`, `menu_type`, `permission`, `visible`, `status`, `system_code`, `create_time`, `update_time`, `deleted`) 
VALUES
-- 主系统菜单
(1, 0, '系统管理', '/system', NULL, 'el-icon-setting', 1, 1, NULL, 1, 1, 'MAIN', NOW(), NOW(), 0),
(2, 1, '用户管理', '/system/user', 'system/user/index', 'el-icon-user', 1, 2, 'system:user:list', 1, 1, 'MAIN', NOW(), NOW(), 0),
(3, 1, '角色管理', '/system/role', 'system/role/index', 'el-icon-s-custom', 2, 2, 'system:role:list', 1, 1, 'MAIN', NOW(), NOW(), 0),
(4, 1, '菜单管理', '/system/menu', 'system/menu/index', 'el-icon-menu', 3, 2, 'system:menu:list', 1, 1, 'MAIN', NOW(), NOW(), 0),
(5, 1, '权限管理', '/system/permission', 'system/permission/index', 'el-icon-key', 4, 2, 'system:permission:list', 1, 1, 'MAIN', NOW(), NOW(), 0),

-- 用户管理按钮权限
(6, 2, '新增用户', NULL, NULL, NULL, 1, 3, 'system:user:add', 0, 1, 'MAIN', NOW(), NOW(), 0),
(7, 2, '编辑用户', NULL, NULL, NULL, 2, 3, 'system:user:edit', 0, 1, 'MAIN', NOW(), NOW(), 0),
(8, 2, '删除用户', NULL, NULL, NULL, 3, 3, 'system:user:delete', 0, 1, 'MAIN', NOW(), NOW(), 0),
(9, 2, '重置密码', NULL, NULL, NULL, 4, 3, 'system:user:resetPwd', 0, 1, 'MAIN', NOW(), NOW(), 0),

-- 角色管理按钮权限
(10, 3, '新增角色', NULL, NULL, NULL, 1, 3, 'system:role:add', 0, 1, 'MAIN', NOW(), NOW(), 0),
(11, 3, '编辑角色', NULL, NULL, NULL, 2, 3, 'system:role:edit', 0, 1, 'MAIN', NOW(), NOW(), 0),
(12, 3, '删除角色', NULL, NULL, NULL, 3, 3, 'system:role:delete', 0, 1, 'MAIN', NOW(), NOW(), 0),
(13, 3, '分配权限', NULL, NULL, NULL, 4, 3, 'system:role:assign', 0, 1, 'MAIN', NOW(), NOW(), 0),

-- 业务模块菜单 (原有的career exploration功能)
(14, 0, '职业探索', '/career', NULL, 'el-icon-data-analysis', 2, 1, NULL, 1, 1, 'MAIN', NOW(), NOW(), 0),
(15, 14, '公司信息', '/career/company', 'career/company/index', 'el-icon-office-building', 1, 2, 'career:company:list', 1, 1, 'MAIN', NOW(), NOW(), 0),
(16, 14, '技术栈统计', '/career/technology', 'career/technology/index', 'el-icon-cpu', 2, 2, 'career:technology:list', 1, 1, 'MAIN', NOW(), NOW(), 0),
(17, 14, '薪资统计', '/career/salary', 'career/salary/index', 'el-icon-money', 3, 2, 'career:salary:list', 1, 1, 'MAIN', NOW(), NOW(), 0),
(18, 14, '问答系统', '/career/qa', 'career/qa/index', 'el-icon-chat-dot-square', 4, 2, 'career:qa:list', 1, 1, 'MAIN', NOW(), NOW(), 0),

-- 子系统入口菜单 (示例)
(19, 0, '子系统入口', '/subsystems', NULL, 'el-icon-link', 3, 1, NULL, 1, 1, 'MAIN', NOW(), NOW(), 0),
(20, 19, '人力资源系统', '/hr-system', NULL, 'el-icon-user-solid', 1, 2, 'subsystem:hr:access', 1, 1, 'HR_SYSTEM', NOW(), NOW(), 0),
(21, 19, '财务管理系统', '/finance-system', NULL, 'el-icon-money', 2, 2, 'subsystem:finance:access', 1, 1, 'FINANCE_SYSTEM', NOW(), NOW(), 0),
(22, 19, '项目管理系统', '/project-system', NULL, 'el-icon-s-management', 3, 2, 'subsystem:project:access', 1, 1, 'PROJECT_SYSTEM', NOW(), NOW(), 0);

-- 插入权限数据
INSERT IGNORE INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `description`, `status`, `create_time`, `update_time`, `deleted`) 
VALUES
-- 系统权限
(1, '用户查看', 'system:user:list', '查看用户列表权限', 1, NOW(), NOW(), 0),
(2, '用户新增', 'system:user:add', '新增用户权限', 1, NOW(), NOW(), 0),
(3, '用户编辑', 'system:user:edit', '编辑用户权限', 1, NOW(), NOW(), 0),
(4, '用户删除', 'system:user:delete', '删除用户权限', 1, NOW(), NOW(), 0),
(5, '重置密码', 'system:user:resetPwd', '重置用户密码权限', 1, NOW(), NOW(), 0),

(6, '角色查看', 'system:role:list', '查看角色列表权限', 1, NOW(), NOW(), 0),
(7, '角色新增', 'system:role:add', '新增角色权限', 1, NOW(), NOW(), 0),
(8, '角色编辑', 'system:role:edit', '编辑角色权限', 1, NOW(), NOW(), 0),
(9, '角色删除', 'system:role:delete', '删除角色权限', 1, NOW(), NOW(), 0),
(10, '分配权限', 'system:role:assign', '为角色分配权限', 1, NOW(), NOW(), 0),

(11, '菜单查看', 'system:menu:list', '查看菜单列表权限', 1, NOW(), NOW(), 0),
(12, '菜单新增', 'system:menu:add', '新增菜单权限', 1, NOW(), NOW(), 0),
(13, '菜单编辑', 'system:menu:edit', '编辑菜单权限', 1, NOW(), NOW(), 0),
(14, '菜单删除', 'system:menu:delete', '删除菜单权限', 1, NOW(), NOW(), 0),

(15, '权限查看', 'system:permission:list', '查看权限列表权限', 1, NOW(), NOW(), 0),
(16, '权限新增', 'system:permission:add', '新增权限权限', 1, NOW(), NOW(), 0),
(17, '权限编辑', 'system:permission:edit', '编辑权限权限', 1, NOW(), NOW(), 0),
(18, '权限删除', 'system:permission:delete', '删除权限权限', 1, NOW(), NOW(), 0),

-- 业务权限
(19, '公司信息查看', 'career:company:list', '查看公司信息权限', 1, NOW(), NOW(), 0),
(20, '技术栈查看', 'career:technology:list', '查看技术栈统计权限', 1, NOW(), NOW(), 0),
(21, '薪资统计查看', 'career:salary:list', '查看薪资统计权限', 1, NOW(), NOW(), 0),
(22, '问答系统查看', 'career:qa:list', '查看问答系统权限', 1, NOW(), NOW(), 0),

-- 子系统访问权限
(23, '人力资源系统访问', 'subsystem:hr:access', '访问人力资源系统权限', 1, NOW(), NOW(), 0),
(24, '财务管理系统访问', 'subsystem:finance:access', '访问财务管理系统权限', 1, NOW(), NOW(), 0),
(25, '项目管理系统访问', 'subsystem:project:access', '访问项目管理系统权限', 1, NOW(), NOW(), 0);

-- 插入用户角色关联
INSERT IGNORE INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1);

-- 为超级管理员角色分配所有菜单
INSERT IGNORE INTO `sys_role_menu` (`role_id`, `menu_id`) 
SELECT 1, id FROM sys_menu WHERE deleted = 0;

-- 为超级管理员角色分配所有权限
INSERT IGNORE INTO `sys_role_permission` (`role_id`, `permission_id`) 
SELECT 1, id FROM sys_permission WHERE deleted = 0;

-- 为系统管理员角色分配系统管理相关权限
INSERT IGNORE INTO `sys_role_permission` (`role_id`, `permission_id`) 
VALUES 
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5),  -- 用户管理权限
(2, 6), (2, 7), (2, 8), (2, 9), (2, 10), -- 角色管理权限
(2, 11), (2, 12), (2, 13), (2, 14),      -- 菜单管理权限
(2, 15), (2, 16), (2, 17), (2, 18);      -- 权限管理权限

-- 为普通用户角色分配基础权限
INSERT IGNORE INTO `sys_role_permission` (`role_id`, `permission_id`) 
VALUES 
(3, 19), (3, 20), (3, 21), (3, 22);  -- 业务查看权限