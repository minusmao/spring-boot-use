/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.164.130
 Source Server Type    : MySQL
 Source Server Version : 50740
 Source Host           : 192.168.164.130:3306
 Source Schema         : spring_boot_use

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 07/12/2022 01:31:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_scheduled_task_info
-- ----------------------------
DROP TABLE IF EXISTS `sms_scheduled_task_info`;
CREATE TABLE `sms_scheduled_task_info`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键id',
  `task_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务键',
  `cron` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'cron表达式',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NOT NULL COMMENT '状态（0-停止 1-启动）',
  `update_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统管理-定时任务信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_scheduled_task_info
-- ----------------------------
INSERT INTO `sms_scheduled_task_info` VALUES ('1', 'com.example.spring.boot.use.schedule.schedule.task.ScheduledTaskJob1', '0/2 * * * * ?', '定时任务1', 1, NULL, '2022-12-07 00:39:52');
INSERT INTO `sms_scheduled_task_info` VALUES ('2', 'com.example.spring.boot.use.schedule.schedule.task.ScheduledTaskJob2', '1/2 * * * * ?', '定时任务2', 1, NULL, '2022-12-07 00:39:52');

SET FOREIGN_KEY_CHECKS = 1;
