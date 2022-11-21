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

 Date: 21/11/2022 22:42:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_class
-- ----------------------------
DROP TABLE IF EXISTS `pms_class`;
CREATE TABLE `pms_class`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键id',
  `number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班级编号',
  `grade` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '年级（如2016级、2017级）',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本',
  `create_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '人员管理-班级' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_class
-- ----------------------------
INSERT INTO `pms_class` VALUES ('1594692554291466241', '201602', '2016', 1, NULL, '2022-11-21 22:01:48', NULL, '2022-11-21 22:18:29', 0);
INSERT INTO `pms_class` VALUES ('1594699230159577089', '201602', '2016', 0, NULL, '2022-11-21 22:28:19', NULL, '2022-11-21 22:28:19', 0);
INSERT INTO `pms_class` VALUES ('1594699256554332161', '201603', '2016', 0, NULL, '2022-11-21 22:28:26', NULL, '2022-11-21 22:28:26', 0);
INSERT INTO `pms_class` VALUES ('1594699270651387905', '201604', '2016', 0, NULL, '2022-11-21 22:28:29', NULL, '2022-11-21 22:28:29', 0);
INSERT INTO `pms_class` VALUES ('1594699283565649922', '201605', '2016', 0, NULL, '2022-11-21 22:28:32', NULL, '2022-11-21 22:28:32', 0);
INSERT INTO `pms_class` VALUES ('1594699299663388673', '201606', '2016', 0, NULL, '2022-11-21 22:28:36', NULL, '2022-11-21 22:28:36', 0);
INSERT INTO `pms_class` VALUES ('1594699325651296258', '201607', '2016', 0, NULL, '2022-11-21 22:28:42', NULL, '2022-11-21 22:28:42', 0);

SET FOREIGN_KEY_CHECKS = 1;
