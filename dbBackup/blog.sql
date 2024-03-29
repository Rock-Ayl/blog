/*
 Navicat Premium Data Transfer

 Source Server         : 本地-mysql
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : 127.0.0.1:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 25/06/2019 16:53:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '评论唯一id',
  `userId` int(20) DEFAULT NULL COMMENT '用户id',
  `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `content` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论',
  `timestamp` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `email` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮件',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
INSERT INTO `comment` VALUES (1, -1, '测试人员1', '自古一楼不简单', 1561429266231, '123@163.com');
INSERT INTO `comment` VALUES (2, -1, '测试人员2', '一楼说得对', 1561429266232, '456@163.com');
INSERT INTO `comment` VALUES (3, 1, '', '二楼说得对', 1561429266233, '456@163.com');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色表唯一id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5016 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, '匿名');
INSERT INTO `role` VALUES (2, '普通用户');
INSERT INTO `role` VALUES (3, 'Vip');
INSERT INTO `role` VALUES (4, '管理员');
COMMIT;

-- ----------------------------
-- Table structure for role_bind_user
-- ----------------------------
DROP TABLE IF EXISTS `role_bind_user`;
CREATE TABLE `role_bind_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户绑定角色表唯一id',
  `userId` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `roleId` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_bind_user
-- ----------------------------
BEGIN;
INSERT INTO `role_bind_user` VALUES (1, 1, 4);
INSERT INTO `role_bind_user` VALUES (2, 2, 2);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(40) DEFAULT NULL COMMENT '电话号码',
  `remark` varchar(80) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'root', '000000', 'root@qq.com', '1555555555', NULL);
INSERT INTO `user` VALUES (2, '普通用户1', '521321', 'genert@163.com', '1555555555', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
