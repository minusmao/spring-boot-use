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

 Date: 21/11/2022 22:42:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_student
-- ----------------------------
DROP TABLE IF EXISTS `pms_student`;
CREATE TABLE `pms_student`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键id',
  `number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别（0-女 1-男）',
  `birth` date NULL DEFAULT NULL COMMENT '生日',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `class_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班级编号',
  `create_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0-否 1-是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '人员管理-学生' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_student
-- ----------------------------
INSERT INTO `pms_student` VALUES ('1594702209864470530', '20160101', '小明', '1', '2000-01-01', '13524684299', '201601', NULL, '2022-11-21 22:40:10', NULL, '2022-11-21 22:40:10', 0);
INSERT INTO `pms_student` VALUES ('1594702394376097794', '20160102', '小红', '0', '2000-02-01', '13523337785', '201601', NULL, '2022-11-21 22:40:54', NULL, '2022-11-21 22:40:54', 0);

SET FOREIGN_KEY_CHECKS = 1;
