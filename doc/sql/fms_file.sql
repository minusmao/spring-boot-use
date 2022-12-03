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

 Date: 04/12/2022 01:14:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fms_file
-- ----------------------------
DROP TABLE IF EXISTS `fms_file`;
CREATE TABLE `fms_file`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型（一般为HttpHeader中的Content-Type）',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `size` int(11) NOT NULL COMMENT '文件大小（单位：字节）',
  `create_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件管理-文件' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
