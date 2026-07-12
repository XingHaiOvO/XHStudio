-- XH Studio website initialization script
-- Target: MySQL 8.0+
-- Notes:
-- 0. This script is intended for first-time initialization on an empty database.
-- 1. The system uses soft delete. Business delete APIs should update deleted = 1.
-- 2. /admin/login and /member/login are page-entry separation only; JWT and account tables are shared.
-- 3. Project owners are not a system role. They are identified by project_member.role_in_project = 'OWNER'
--    and must use /member/project/** APIs.

CREATE DATABASE IF NOT EXISTS `xh_studio`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE `xh_studio`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `role_name` VARCHAR(30) NOT NULL COMMENT 'Role display name',
  `role_code` VARCHAR(30) NOT NULL COMMENT 'SUPER_ADMIN / ADMIN / MEMBER',
  `description` VARCHAR(100) DEFAULT NULL COMMENT 'Role description',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`),
  KEY `idx_role_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='RBAC roles';

CREATE TABLE IF NOT EXISTS `menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT 'Parent menu id, 0 means root',
  `menu_name` VARCHAR(50) NOT NULL COMMENT 'Menu name',
  `path` VARCHAR(100) DEFAULT NULL COMMENT 'Frontend route path',
  `component` VARCHAR(100) DEFAULT NULL COMMENT 'Frontend component path',
  `icon` VARCHAR(50) DEFAULT NULL COMMENT 'Icon name',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT 'Sort order',
  `menu_type` TINYINT NOT NULL DEFAULT 1 COMMENT '0-directory, 1-menu, 2-button',
  `visible` TINYINT NOT NULL DEFAULT 1 COMMENT '0-hidden, 1-visible',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_menu_parent_sort` (`parent_id`, `sort_order`),
  KEY `idx_menu_visible` (`visible`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Admin menu tree';

CREATE TABLE IF NOT EXISTS `role_menu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `role_id` BIGINT NOT NULL COMMENT 'Role id',
  `menu_id` BIGINT NOT NULL COMMENT 'Menu id',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`),
  KEY `idx_role_menu_menu` (`menu_id`),
  CONSTRAINT `fk_role_menu_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `fk_role_menu_menu` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Role-menu relation';

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `username` VARCHAR(50) NOT NULL COMMENT 'Login account',
  `password` VARCHAR(255) NOT NULL COMMENT 'BCrypt password hash',
  `nickname` VARCHAR(50) NOT NULL COMMENT 'Nickname',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT 'Avatar URL',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT 'Phone number',
  `email` VARCHAR(100) NOT NULL COMMENT 'Email',
  `qq` VARCHAR(20) NOT NULL COMMENT 'QQ number for member verification',
  `role_id` BIGINT NOT NULL COMMENT 'Role id',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '0-disabled, 1-enabled',
  `join_year` INT DEFAULT NULL COMMENT 'Join year',
  `intro` TEXT DEFAULT NULL COMMENT 'Rich text profile',
  `honor_badge` VARCHAR(100) DEFAULT NULL COMMENT 'Comma-separated honor badges',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_role_id` (`role_id`),
  KEY `idx_user_status` (`status`, `deleted`),
  KEY `idx_user_join_year` (`join_year`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `chk_user_status` CHECK (`status` IN (0, 1)),
  CONSTRAINT `chk_user_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Members and administrators';

CREATE TABLE IF NOT EXISTS `project` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `title` VARCHAR(100) NOT NULL COMMENT 'Project title',
  `cover` VARCHAR(255) DEFAULT NULL COMMENT 'Cover image URL',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0-planned, 1-in progress, 2-completed',
  `progress` TINYINT NOT NULL DEFAULT 0 COMMENT 'Progress percentage, 0-100',
  `summary` VARCHAR(200) DEFAULT NULL COMMENT 'Short summary',
  `content` LONGTEXT DEFAULT NULL COMMENT 'Rich text content',
  `start_time` DATE DEFAULT NULL COMMENT 'Start date',
  `end_time` DATE DEFAULT NULL COMMENT 'End date',
  `project_url` VARCHAR(255) DEFAULT NULL COMMENT 'External URL for completed project',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT 'Sort order',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_project_status_sort` (`status`, `sort_order`, `create_time`),
  KEY `idx_project_deleted` (`deleted`),
  KEY `idx_project_title` (`title`),
  CONSTRAINT `chk_project_status` CHECK (`status` IN (0, 1, 2)),
  CONSTRAINT `chk_project_progress` CHECK (`progress` BETWEEN 0 AND 100),
  CONSTRAINT `chk_project_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Studio projects';

CREATE TABLE IF NOT EXISTS `project_image` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `project_id` BIGINT NOT NULL COMMENT 'Project id',
  `image_url` VARCHAR(255) NOT NULL COMMENT 'Image URL',
  `description` VARCHAR(200) DEFAULT NULL COMMENT 'Image description',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT 'Sort order',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_project_image_project_sort` (`project_id`, `sort_order`),
  CONSTRAINT `fk_project_image_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `chk_project_image_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Project gallery images';

CREATE TABLE IF NOT EXISTS `project_milestone` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `project_id` BIGINT NOT NULL COMMENT 'Project id',
  `title` VARCHAR(100) NOT NULL COMMENT 'Milestone title',
  `planned_date` DATE DEFAULT NULL COMMENT 'Planned date',
  `actual_date` DATE DEFAULT NULL COMMENT 'Actual date',
  `completed` TINYINT NOT NULL DEFAULT 0 COMMENT '0-not completed, 1-completed',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT 'Sort order',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_milestone_project_sort` (`project_id`, `sort_order`),
  KEY `idx_milestone_completed` (`completed`, `deleted`),
  CONSTRAINT `fk_milestone_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `chk_milestone_completed` CHECK (`completed` IN (0, 1)),
  CONSTRAINT `chk_milestone_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Project milestones';

CREATE TABLE IF NOT EXISTS `project_member` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `project_id` BIGINT NOT NULL COMMENT 'Project id',
  `user_id` BIGINT NOT NULL COMMENT 'Member id',
  `role_in_project` VARCHAR(20) NOT NULL DEFAULT 'PARTICIPANT' COMMENT 'OWNER or PARTICIPANT',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted relation',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_pm_user_role` (`user_id`, `role_in_project`, `deleted`),
  KEY `idx_pm_project_role` (`project_id`, `role_in_project`, `deleted`),
  CONSTRAINT `fk_pm_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `fk_pm_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `chk_pm_role` CHECK (`role_in_project` IN ('OWNER', 'PARTICIPANT')),
  CONSTRAINT `chk_pm_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Project-member relation; project owner permission source';

CREATE TABLE IF NOT EXISTS `member_application` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `login_account` VARCHAR(50) NOT NULL COMMENT 'Requested login account',
  `name` VARCHAR(50) NOT NULL COMMENT 'Applicant name',
  `email` VARCHAR(100) NOT NULL COMMENT 'Applicant email',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT 'Applicant phone',
  `intro` TEXT DEFAULT NULL COMMENT 'Short introduction',
  `apply_no` VARCHAR(32) NOT NULL COMMENT 'Public application number',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '0-pending, 1-approved, 2-rejected',
  `reject_reason` VARCHAR(200) DEFAULT NULL COMMENT 'Reject reason',
  `processed_by` BIGINT DEFAULT NULL COMMENT 'Processor user id',
  `processed_time` DATETIME DEFAULT NULL COMMENT 'Processed time',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_apply_no` (`apply_no`),
  KEY `idx_app_status_create` (`status`, `create_time`),
  KEY `idx_app_processed_by` (`processed_by`),
  CONSTRAINT `fk_app_processed_by` FOREIGN KEY (`processed_by`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT `chk_app_status` CHECK (`status` IN (0, 1, 2)),
  CONSTRAINT `chk_app_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Member join applications';

CREATE TABLE IF NOT EXISTS `work` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `user_id` BIGINT NOT NULL COMMENT 'Owner user id',
  `title` VARCHAR(100) NOT NULL COMMENT 'Work title',
  `cover` VARCHAR(255) DEFAULT NULL COMMENT 'Cover URL',
  `description` TEXT DEFAULT NULL COMMENT 'Rich text description',
  `link` VARCHAR(255) DEFAULT NULL COMMENT 'External link',
  `file_url` VARCHAR(255) DEFAULT NULL COMMENT 'Attachment URL',
  `tags` VARCHAR(100) DEFAULT NULL COMMENT 'Comma-separated tags',
  `is_public` TINYINT NOT NULL DEFAULT 1 COMMENT '0-private, 1-public',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_work_user_time` (`user_id`, `create_time`),
  KEY `idx_work_public_time` (`is_public`, `deleted`, `create_time`),
  KEY `idx_work_title` (`title`),
  CONSTRAINT `fk_work_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `chk_work_public` CHECK (`is_public` IN (0, 1)),
  CONSTRAINT `chk_work_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Member works';

CREATE TABLE IF NOT EXISTS `growth_experience` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `user_id` BIGINT NOT NULL COMMENT 'Owner user id',
  `happen_date` DATE DEFAULT NULL COMMENT 'Experience date',
  `title` VARCHAR(100) NOT NULL COMMENT 'Title',
  `content` TEXT DEFAULT NULL COMMENT 'Rich text content',
  `image_url` VARCHAR(255) DEFAULT NULL COMMENT 'Image URL',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT 'Sort order',
  `is_public` TINYINT NOT NULL DEFAULT 1 COMMENT '0-private, 1-public',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_growth_user_sort` (`user_id`, `sort_order`, `happen_date`),
  KEY `idx_growth_public_time` (`is_public`, `deleted`, `happen_date`),
  CONSTRAINT `fk_growth_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `chk_growth_public` CHECK (`is_public` IN (0, 1)),
  CONSTRAINT `chk_growth_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Member growth experiences';

CREATE TABLE IF NOT EXISTS `announcement` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `title` VARCHAR(100) NOT NULL COMMENT 'Announcement title',
  `content` LONGTEXT NOT NULL COMMENT 'Rich text content',
  `is_top` TINYINT NOT NULL DEFAULT 0 COMMENT '0-normal, 1-top',
  `published_at` DATETIME DEFAULT NULL COMMENT 'Published time',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0-active, 1-soft deleted',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_announcement_publish` (`deleted`, `is_top`, `published_at`),
  CONSTRAINT `chk_announcement_top` CHECK (`is_top` IN (0, 1)),
  CONSTRAINT `chk_announcement_deleted` CHECK (`deleted` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Public announcements';

CREATE TABLE IF NOT EXISTS `banner` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `image_url` VARCHAR(255) NOT NULL COMMENT 'Image URL',
  `link_url` VARCHAR(255) DEFAULT NULL COMMENT 'Link URL',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT 'Sort order',
  `enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '0-disabled, 1-enabled',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_banner_enabled_sort` (`enabled`, `sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Homepage banners';

CREATE TABLE IF NOT EXISTS `notification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `user_id` BIGINT NOT NULL COMMENT 'Receiver user id',
  `title` VARCHAR(100) NOT NULL COMMENT 'Notification title',
  `content` TEXT NOT NULL COMMENT 'Notification content',
  `is_read` TINYINT NOT NULL DEFAULT 0 COMMENT '0-unread, 1-read',
  `type` TINYINT NOT NULL DEFAULT 0 COMMENT '0-SYSTEM (includes approval result), 1-ADMIN_SEND',
  `batch_id` VARCHAR(32) DEFAULT NULL COMMENT 'Batch id for admin-send aggregation',
  `sender_id` BIGINT DEFAULT NULL COMMENT 'Sender user id',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_notif_user_read_time` (`user_id`, `is_read`, `create_time`),
  KEY `idx_notif_batch` (`batch_id`),
  KEY `idx_notif_sender` (`sender_id`),
  CONSTRAINT `fk_notification_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT `fk_notification_sender` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT `chk_notification_read` CHECK (`is_read` IN (0, 1)),
  CONSTRAINT `chk_notification_type` CHECK (`type` IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Write-fanout in-app notifications';

CREATE TABLE IF NOT EXISTS `friend_link` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `name` VARCHAR(50) NOT NULL COMMENT 'Link name',
  `url` VARCHAR(255) NOT NULL COMMENT 'Link URL',
  `logo` VARCHAR(255) DEFAULT NULL COMMENT 'Logo URL',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT 'Sort order',
  `enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '0-disabled, 1-enabled',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_friend_enabled_sort` (`enabled`, `sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Friend links';

CREATE TABLE IF NOT EXISTS `site_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `config_key` VARCHAR(50) NOT NULL COMMENT 'Config key',
  `config_value` TEXT DEFAULT NULL COMMENT 'Config value',
  `description` VARCHAR(100) DEFAULT NULL COMMENT 'Description',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_site_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Website configuration';

CREATE TABLE IF NOT EXISTS `operation_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `user_id` BIGINT DEFAULT NULL COMMENT 'Operator user id',
  `username` VARCHAR(50) DEFAULT NULL COMMENT 'Operator username snapshot',
  `operation` VARCHAR(100) NOT NULL COMMENT 'Operation description',
  `method` VARCHAR(200) DEFAULT NULL COMMENT 'HTTP method and URL',
  `params` TEXT DEFAULT NULL COMMENT 'Sanitized params',
  `ip` VARCHAR(50) DEFAULT NULL COMMENT 'IP address',
  `result` VARCHAR(20) NOT NULL DEFAULT 'success' COMMENT 'success / fail',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_op_log_time` (`create_time`),
  KEY `idx_op_log_user` (`user_id`, `create_time`),
  CONSTRAINT `fk_operation_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT `chk_operation_result` CHECK (`result` IN ('success', 'fail'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Admin operation logs';

CREATE TABLE IF NOT EXISTS `login_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `user_id` BIGINT DEFAULT NULL COMMENT 'Login user id',
  `username` VARCHAR(50) NOT NULL COMMENT 'Login account snapshot',
  `login_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Login time',
  `ip` VARCHAR(50) DEFAULT NULL COMMENT 'IP address',
  `user_agent` VARCHAR(500) DEFAULT NULL COMMENT 'User-Agent',
  `result` VARCHAR(20) NOT NULL DEFAULT 'success' COMMENT 'success / fail',
  `fail_reason` VARCHAR(100) DEFAULT NULL COMMENT 'Fail reason',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_login_user_time` (`user_id`, `login_time`),
  KEY `idx_login_time` (`login_time`),
  CONSTRAINT `fk_login_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT `chk_login_result` CHECK (`result` IN ('success', 'fail'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Login logs, archive periodically';

-- Soft-delete aware unique indexes. MySQL unique indexes allow multiple NULL values.
CREATE UNIQUE INDEX `uk_user_username_active`
  ON `user` ((CASE WHEN `deleted` = 0 THEN `username` ELSE NULL END));

CREATE UNIQUE INDEX `uk_user_qq_active`
  ON `user` ((CASE WHEN `deleted` = 0 THEN `qq` ELSE NULL END));

CREATE UNIQUE INDEX `uk_app_login_account_active`
  ON `member_application` ((CASE WHEN `deleted` = 0 THEN `login_account` ELSE NULL END));

CREATE UNIQUE INDEX `uk_pm_project_user_active`
  ON `project_member` ((CASE WHEN `deleted` = 0 THEN `project_id` ELSE NULL END),
                       (CASE WHEN `deleted` = 0 THEN `user_id` ELSE NULL END));

SET FOREIGN_KEY_CHECKS = 1;

-- RBAC seed data
INSERT INTO `role` (`id`, `role_name`, `role_code`, `description`, `deleted`)
VALUES
  (1, '超级管理员', 'SUPER_ADMIN', '系统最高权限，包含系统配置、日志和管理员账号管理', 0),
  (2, '管理员', 'ADMIN', '日常运营管理，可管理项目、成员、申请、通知等', 0),
  (3, '普通成员', 'MEMBER', '成员后台用户，可管理个人资料、作品和成长经历', 0)
ON DUPLICATE KEY UPDATE
  `role_name` = VALUES(`role_name`),
  `description` = VALUES(`description`),
  `deleted` = VALUES(`deleted`);

INSERT INTO `menu` (`id`, `parent_id`, `menu_name`, `path`, `component`, `icon`, `sort_order`, `menu_type`, `visible`, `deleted`)
VALUES
  (1, 0, '仪表盘', '/admin/dashboard', 'dashboard/index', 'dashboard', 1, 1, 1, 0),
  (2, 0, '工作室管理', '/admin/studio', NULL, 'studio', 2, 0, 1, 0),
  (3, 2, '项目管理', '/admin/project', 'project/index', 'project', 1, 1, 1, 0),
  (4, 2, '成员管理', '/admin/member', 'member/index', 'member', 2, 1, 1, 0),
  (5, 2, '加入审批', '/admin/apply', 'apply/index', 'apply', 3, 1, 1, 0),
  (6, 2, '个人成果全局管理', '/admin/works', 'works/index', 'work', 4, 1, 1, 0),
  (7, 2, '通知管理', '/admin/notification', 'notification/index', 'notification', 5, 1, 1, 0),
  (8, 0, '系统管理', '/admin/system', NULL, 'system', 3, 0, 1, 0),
  (9, 8, '网站设置', '/admin/system/website', 'system/website/index', 'setting', 1, 1, 1, 0),
  (10, 8, '轮播图管理', '/admin/system/banner', 'system/banner/index', 'banner', 2, 1, 1, 0),
  (11, 8, '公告管理', '/admin/system/notice', 'system/notice/index', 'notice', 3, 1, 1, 0),
  (12, 8, '友情链接管理', '/admin/system/friend-link', 'system/friend-link/index', 'link', 4, 1, 1, 0),
  (13, 8, '日志查看', '/admin/system/log', 'system/log/index', 'log', 5, 1, 1, 0),
  (14, 8, '管理员账号', '/admin/system/admins', 'system/admins/index', 'admin', 6, 1, 1, 0)
ON DUPLICATE KEY UPDATE
  `parent_id` = VALUES(`parent_id`),
  `menu_name` = VALUES(`menu_name`),
  `path` = VALUES(`path`),
  `component` = VALUES(`component`),
  `icon` = VALUES(`icon`),
  `sort_order` = VALUES(`sort_order`),
  `menu_type` = VALUES(`menu_type`),
  `visible` = VALUES(`visible`),
  `deleted` = VALUES(`deleted`);

INSERT IGNORE INTO `role_menu` (`role_id`, `menu_id`)
SELECT 1, `id` FROM `menu` WHERE `id` BETWEEN 1 AND 14;

INSERT IGNORE INTO `role_menu` (`role_id`, `menu_id`)
SELECT 2, `id` FROM `menu` WHERE `id` BETWEEN 1 AND 7;

-- Initial website configs
INSERT INTO `site_config` (`config_key`, `config_value`, `description`)
VALUES
  ('studio_name', '星海工作室', '工作室名称'),
  ('logo', '', 'Logo URL'),
  ('ip_name', '柚米 YUMI', '品牌 IP 名称'),
  ('contact_email', 'admin@example.com', '联系邮箱'),
  ('contact_phone', '', '联系电话'),
  ('icp', '', 'ICP备案号'),
  ('footer_copyright', 'Copyright © 星海工作室', '页脚版权信息'),
  ('about_us', '<p>星海工作室专注于开发、设计、内容与 ACG 创作协作。</p>', '关于我们富文本')
ON DUPLICATE KEY UPDATE
  `config_value` = VALUES(`config_value`),
  `description` = VALUES(`description`);

-- Initial super administrator.
-- Demo password: password
-- Replace the password hash before production.
INSERT INTO `user` (
  `id`, `username`, `password`, `nickname`, `avatar`, `phone`, `email`, `qq`,
  `role_id`, `status`, `join_year`, `intro`, `honor_badge`, `deleted`
) VALUES (
  1,
  'admin',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
  '超级管理员',
  NULL,
  NULL,
  'admin@example.com',
  '10000',
  1,
  1,
  YEAR(CURDATE()),
  '系统初始化超级管理员',
  '创始管理员',
  0
) ON DUPLICATE KEY UPDATE
  `nickname` = VALUES(`nickname`),
  `role_id` = VALUES(`role_id`),
  `status` = VALUES(`status`),
  `deleted` = VALUES(`deleted`);

-- Sample public content, safe to remove.
INSERT INTO `banner` (`id`, `image_url`, `link_url`, `sort_order`, `enabled`)
VALUES
  (1, '/uploads/banner/yumi-welcome.png', '/project', 1, 1),
  (2, '/uploads/banner/join-us.png', '/join', 2, 1)
ON DUPLICATE KEY UPDATE
  `image_url` = VALUES(`image_url`),
  `link_url` = VALUES(`link_url`),
  `sort_order` = VALUES(`sort_order`),
  `enabled` = VALUES(`enabled`);

INSERT INTO `announcement` (`id`, `title`, `content`, `is_top`, `published_at`, `deleted`)
VALUES
  (1, '工作室系统初始化完成', '<p>欢迎使用星海工作室官网与成员后台。</p>', 1, NOW(), 0)
ON DUPLICATE KEY UPDATE
  `title` = VALUES(`title`),
  `content` = VALUES(`content`),
  `is_top` = VALUES(`is_top`),
  `deleted` = VALUES(`deleted`);
