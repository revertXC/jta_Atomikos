/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 127.0.0.1:3306
 Source Schema         : simplepro

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 19/06/2019 16:45:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_t_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_t_user`;
CREATE TABLE `sys_t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `deleted` int(1) NULL DEFAULT 0,
  `status` int(11) NULL DEFAULT 1,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_by` bigint(255) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `display` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_t_user
-- ----------------------------
INSERT INTO `sys_t_user` VALUES (1, '张三', 'zhangSan', '123456', 0, 1, '2019-06-18 17:18:47', NULL, '2019-06-18 17:18:47', NULL, 1);
INSERT INTO `sys_t_user` VALUES (2, '李四', 'liSi', '123456', 0, 1, '2019-06-18 17:19:17', NULL, '2019-06-18 17:18:47', NULL, 1);
INSERT INTO `sys_t_user` VALUES (3, '王五', 'wangWu', '123456', 0, 1, '2019-06-18 17:20:59', NULL, '2019-06-18 17:18:47', NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
