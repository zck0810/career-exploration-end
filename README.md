# 内容管理系统 (Content Management System)

这是一个统一的内容管理系统，作为多个独立子系统的入口点，实现了统一的用户、角色和菜单权限管理。

## 系统架构

### 核心特性
- **单点登录(SSO)**: 用户只需在主系统登录，即可访问所有有权限的子系统
- **统一权限管理**: 所有系统共享用户、角色、菜单权限数据
- **多系统支持**: 支持多个独立的子系统，每个子系统有自己的前后端
- **分层权限控制**: 支持菜单级和按钮级权限控制
- **JWT认证**: 基于JWT的跨系统认证机制

### 技术栈
- **后端**: Java 8 + Spring Boot 2.7.16 + MyBatis Plus
- **数据库**: MySQL + Redis
- **认证**: Spring Security + JWT
- **前端**: Vue (推荐)

## 数据库设计

### 核心表结构
```sql
-- 用户表
sys_user (id, username, password, real_name, email, phone, status, ...)

-- 角色表  
sys_role (id, role_name, role_code, description, status, ...)

-- 菜单表 (支持多系统)
sys_menu (id, parent_id, menu_name, path, system_code, external_url, ...)

-- 权限表
sys_permission (id, permission_name, permission_code, description, ...)

-- 关联表
sys_user_role (user_id, role_id)
sys_role_menu (role_id, menu_id)  
sys_role_permission (role_id, permission_id)
```

### 初始化数据
执行SQL脚本初始化系统：
```bash
# 1. 创建表结构
mysql> source src/main/resources/sql/init.sql

# 2. 插入初始数据  
mysql> source src/main/resources/sql/data.sql
```

默认管理员账号：
- 用户名: `admin`
- 密码: `admin123`

## API文档

### 认证相关
```http
# 用户登录
POST /auth/login
Content-Type: application/json
{
  "username": "admin",
  "password": "admin123"
}

# 用户注销
POST /auth/logout
Authorization: Bearer <token>

# 获取当前用户信息
GET /auth/userinfo
Authorization: Bearer <token>
```

### 用户管理
```http
# 分页查询用户
POST /user/listPage
# 获取所有用户
GET /user/list
# 新增用户
POST /user/save
# 更新用户
POST /user/update
# 删除用户
DELETE /user/{id}
```

### 菜单管理
```http
# 获取用户菜单树
GET /menu/userMenuTree/{userId}
# 获取所有菜单树
GET /menu/allMenuTree
# 根据系统编码获取菜单
GET /menu/system/{systemCode}
```

### 跨系统认证(SSO)
```http
# 验证Token
POST /sso/validateToken?token=<jwt_token>

# 获取用户在指定系统的菜单
GET /sso/userMenus/{systemCode}?token=<jwt_token>

# 生成子系统跳转URL
POST /sso/generateRedirectUrl?systemCode=HR_SYSTEM&token=<jwt_token>

# 检查系统访问权限
GET /sso/checkSystemAccess/{systemCode}?token=<jwt_token>
```

## 子系统集成

### 1. 认证集成
子系统可以通过以下方式验证用户身份：
```javascript
// 从URL参数获取token
const token = new URLSearchParams(location.search).get('token');

// 验证token
fetch('http://main-system/sso/validateToken', {
  method: 'POST',
  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
  body: `token=${token}`
})
.then(response => response.json())
.then(data => {
  if (data.code === 20000) {
    // token有效，保存用户信息
    localStorage.setItem('userInfo', JSON.stringify(data.data));
  }
});
```

### 2. 权限控制
```javascript
// 获取用户权限
const userInfo = JSON.parse(localStorage.getItem('userInfo'));
const permissions = userInfo.permissions;

// 检查权限
function hasPermission(permission) {
  return permissions.includes(permission);
}

// 在Vue组件中使用
v-if="hasPermission('system:user:add')"
```

### 3. 菜单集成
```javascript
// 获取当前系统菜单
fetch(`http://main-system/sso/userMenus/HR_SYSTEM?token=${token}`)
.then(response => response.json())
.then(data => {
  if (data.code === 20000) {
    // 动态生成菜单
    this.menuData = data.data;
  }
});
```

## 系统配置

### application.yml
```yaml
server:
  port: 8520

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/careerexploration
    username: root
    password: root
  redis:
    host: localhost
    port: 6379

jwt:
  secret: careerExplorationSecretKey2024
  expiration: 86400000  # 24小时
  header: Authorization
  prefix: Bearer
```

## 部署说明

### 1. 环境要求
- JDK 8+
- MySQL 5.7+
- Redis 3.0+

### 2. 构建运行
```bash
# 编译项目
mvn clean compile

# 打包
mvn clean package

# 运行
java -jar target/career-exploration-end-0.0.1-SNAPSHOT.jar
```

### 3. Docker部署
```dockerfile
FROM openjdk:8-jre-alpine
COPY target/career-exploration-end-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8520
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 扩展指南

### 添加新的子系统
1. 在`sys_menu`表中添加新系统的菜单记录，设置`system_code`
2. 在`sys_permission`表中添加对应的访问权限
3. 为相关角色分配新系统的权限
4. 在`SSOController`中配置新系统的跳转URL

### 自定义权限控制
```java
@PreAuthorize("hasAuthority('system:user:add')")
@PostMapping("/save")
public Result save(@RequestBody User user) {
    // 方法实现
}
```

## 常见问题

### Q1: Token过期处理
A: 系统会自动检查token有效性，过期后需要重新登录

### Q2: 跨域问题
A: 已配置CORS支持，允许所有域名访问

### Q3: 权限更新不及时
A: 权限变更后，用户需要重新登录才能生效

## 更新日志

### v1.0.0 (2024-09-04)
- 初始版本发布
- 实现基础的用户、角色、权限管理
- 支持JWT认证和跨系统单点登录
- 完整的菜单权限体系
- 提供完整的REST API