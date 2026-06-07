-- mysql 8.0
CREATE DATABASE IF NOT EXISTS `qtj_manage` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `qtj_manage`;

-- 用户表（sys_user）：主键，用户名（唯一值），密码（MD5），昵称，邮箱，手机号，状态（0=禁用、1=启用，索引），是否删除（0=未删除、1=已删除，索引），创建时间，更新时间
CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(50) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态（0=禁用、1=启用）',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除（0=未删除、1=已删除）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 自动更新
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_status` (`status`),
    KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表（sys_role）：主键，角色名，角色编码（唯一值），描述，状态，是否删除（0=禁用、1=启用），创建时间，更新时间
CREATE TABLE IF NOT EXISTS `sys_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_name` VARCHAR(50) NOT NULL COMMENT '角色名',
    `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态（0=禁用、1=启用）',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除（0=未删除、1=已删除）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 自动更新
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`),
    KEY `idx_status` (`status`),
    KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表（sys_permission）：主键，权限名，权限编码，描述，权限类型，父权限ID，路径，组件，图标，状态，是否删除（0=禁用、1=启用），创建时间，更新时间
CREATE TABLE IF NOT EXISTS `sys_permission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `permission_name` VARCHAR(50) NOT NULL COMMENT '权限名',
    `permission_code` VARCHAR(50) NOT NULL COMMENT '权限编码',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
    `permission_type` VARCHAR(20) NOT NULL COMMENT '权限类型（menu、button）',
    `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父权限ID',
    `path` VARCHAR(255) DEFAULT NULL COMMENT '路由路径',
    `component` VARCHAR(255) DEFAULT NULL COMMENT '组件的路径',
    `icon` VARCHAR(255) DEFAULT NULL COMMENT '图标',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态（0=禁用、1=启用）',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除（0=未删除、1=已删除）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 自动更新
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_permission_code` (`permission_code`),
    KEY `idx_status` (`status`),
    KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 用户角色关联（sys_user_role）：主键，用户ID，角色ID
CREATE TABLE IF NOT EXISTS `sys_user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id_role_id` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联';

-- 角色权限关联（sys_role_permission）：主键，角色ID，权限ID
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_id_permission_id` (`role_id`, `permission_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联';

-- 创建管理员账户
INSERT INTO `sys_user`(`username`, `password`, `nickname`, `email`, `phone`, `status`, `is_deleted`)
VALUES ('admin', '123456', '管理员', '', '', 1, 0);

-- 创建角色：管理员、普通用户
INSERT INTO `sys_role`(`role_name`, `role_code`, `description`, `status`, `is_deleted`)
VALUES ('管理员', 'admin', '管理员', 1, 0),
       ('普通用户', 'user', '普通用户', 1, 0);

-- 创建默认权限：用户管理，角色管理，权限管理
BEGIN;
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `description`, `permission_type`, `parent_id`, `path`, `component`, `icon`, `status`, `is_deleted`)
VALUES (1, '用户管理', 'user', '用户管理', 1, 0, '/user', '', '', 1, 0),
(2, '角色管理', 'role', '角色管理', 1, 0, '/role', '', '', 1, 0),
(3, '权限管理', 'permission', '权限管理', 1, 0, '/permission', '', '', 1, 0);
COMMIT;