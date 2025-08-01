/*
 Navicat Premium Data Transfer

 Source Server         : 本地电脑
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : genvideosample

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 31/07/2025 16:19:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pl_resource
-- ----------------------------
DROP TABLE IF EXISTS `pl_resource`;
CREATE TABLE `pl_resource`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '类型',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Y' COMMENT '是否有效',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '资源表，管理上传的图片、视频、音频' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pl_resource
-- ----------------------------
INSERT INTO `pl_resource` VALUES (12, '小孩-demo', '1753946918057_qchtvj.mp4', '2', 'Y', '小孩-demo', '2025-07-31 15:28:38');

-- ----------------------------
-- Table structure for pl_script_gen
-- ----------------------------
DROP TABLE IF EXISTS `pl_script_gen`;
CREATE TABLE `pl_script_gen`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `all` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '完整文案',
  `gen_video_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成的视频名称',
  `video` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '主视频',
  `audio` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '主音频',
  `pic` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '主图',
  `h1` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ai补全1',
  `y1` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '服务配置',
  `h2` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ai补全2',
  `y2` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '连接配置',
  `h3` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ai补全3',
  `y3` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '分析配置',
  `y31` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '分析片段配置',
  `h4` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ai补全4',
  `y4` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '案例配置',
  `y41` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '案例片段配置',
  `h5` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ai补全5',
  `y5` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '转换配置1',
  `h6` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ai补全6',
  `y6` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '转换配置2',
  `h7` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ai补全7',
  `y7` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '转换配置3',
  `h8` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ai补全8',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '类型',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Y' COMMENT '等待执行',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '文案最终优化后表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pl_script_gen
-- ----------------------------
INSERT INTO `pl_script_gen` VALUES (29, '星期天下午，妈妈带我去人民公园玩。刚走到门口，我就看见两棵大槐树像卫兵一样站在两边，树枝上还挂着五颜六色的小灯笼，风一吹，灯笼晃来晃去，好像在对我们招手。​\n进了公园，脚下的石板路弯弯曲曲的，像一条长蛇。路两旁的草坪绿油油的，上面开着黄灿灿的小野花，有蜜蜂在花丛中嗡嗡地飞，好像在唱歌。我蹲下来想摸一摸花瓣，妈妈说：“要爱护花草哦。” 我赶紧收回手，对着小花笑了笑。​', '20250731161725596', '1753946918057_qchtvj.mp4', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2', 'N', NULL, '2025-07-31 15:42:20');

-- ----------------------------
-- Table structure for pl_user
-- ----------------------------
DROP TABLE IF EXISTS `pl_user`;
CREATE TABLE `pl_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称（可以直接使用手机号码）',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Y' COMMENT '是否有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10244 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pl_user
-- ----------------------------
INSERT INTO `pl_user` VALUES (10243, '888888', 'system', 'Y', '2025-06-12 21:13:51');

-- ----------------------------
-- Table structure for pl_video
-- ----------------------------
DROP TABLE IF EXISTS `pl_video`;
CREATE TABLE `pl_video`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '类型',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Y' COMMENT '是否有效',
  `video_id` int NOT NULL COMMENT '对应资源表中的视频id',
  `odds` int NOT NULL DEFAULT 1 COMMENT '概率，1-10，越大概率越高',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '描述',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '讲解人背景视频表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pl_video
-- ----------------------------
INSERT INTO `pl_video` VALUES (101, '1', 'N', 5, 8, '产品介绍专业版视频，适用于高端客户展示', '2025-06-11 19:58:39');
INSERT INTO `pl_video` VALUES (103, '1', 'Y', 12, 5, NULL, '2025-07-31 15:41:55');

SET FOREIGN_KEY_CHECKS = 1;
