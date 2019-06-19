/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 127.0.0.1:3306
 Source Schema         : test_01

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 19/06/2019 16:45:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_t_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_t_oper_log`;
CREATE TABLE `sys_t_oper_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(255) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_t_oper_log
-- ----------------------------
INSERT INTO `sys_t_oper_log` VALUES (17, 1, '默认备注参数', '2019-06-19 16:08:45');

SET FOREIGN_KEY_CHECKS = 1;
