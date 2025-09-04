-- H2 Database Schema for Content Management System

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  real_name VARCHAR(50),
  email VARCHAR(100),
  phone VARCHAR(20),
  avatar VARCHAR(255),
  status TINYINT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  create_by BIGINT,
  update_by BIGINT,
  deleted TINYINT DEFAULT 0
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_username ON sys_user(username);
CREATE INDEX IF NOT EXISTS idx_email ON sys_user(email);
CREATE INDEX IF NOT EXISTS idx_phone ON sys_user(phone);

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  role_name VARCHAR(50) NOT NULL,
  role_code VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  status TINYINT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  create_by BIGINT,
  update_by BIGINT,
  deleted TINYINT DEFAULT 0
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_role_code ON sys_role(role_code);

-- 菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  parent_id BIGINT DEFAULT 0,
  menu_name VARCHAR(100) NOT NULL,
  path VARCHAR(255),
  component VARCHAR(255),
  icon VARCHAR(100),
  sort INT DEFAULT 0,
  menu_type TINYINT DEFAULT 1,
  permission VARCHAR(100),
  visible TINYINT DEFAULT 1,
  status TINYINT DEFAULT 1,
  system_code VARCHAR(50) DEFAULT 'MAIN',
  external_url VARCHAR(500),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  create_by BIGINT,
  update_by BIGINT,
  deleted TINYINT DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_parent_id ON sys_menu(parent_id);
CREATE INDEX IF NOT EXISTS idx_system_code ON sys_menu(system_code);

-- 权限表
CREATE TABLE IF NOT EXISTS sys_permission (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  permission_name VARCHAR(50) NOT NULL,
  permission_code VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  status TINYINT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  create_by BIGINT,
  update_by BIGINT,
  deleted TINYINT DEFAULT 0
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_permission_code ON sys_permission(permission_code);

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_user_role ON sys_user_role(user_id, role_id);
CREATE INDEX IF NOT EXISTS idx_user_id ON sys_user_role(user_id);
CREATE INDEX IF NOT EXISTS idx_role_id ON sys_user_role(role_id);

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  role_id BIGINT NOT NULL,
  menu_id BIGINT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_role_menu ON sys_role_menu(role_id, menu_id);
CREATE INDEX IF NOT EXISTS idx_role_id_menu ON sys_role_menu(role_id);
CREATE INDEX IF NOT EXISTS idx_menu_id ON sys_role_menu(menu_id);

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS sys_role_permission (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  role_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_role_permission ON sys_role_permission(role_id, permission_id);
CREATE INDEX IF NOT EXISTS idx_role_id_perm ON sys_role_permission(role_id);
CREATE INDEX IF NOT EXISTS idx_permission_id ON sys_role_permission(permission_id);