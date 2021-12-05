/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : ehr

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 05/12/2021 16:05:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '人事部', '负责人员招聘管理', NULL, NULL);
INSERT INTO `department` VALUES (2, '研发部门', '技术开发', NULL, NULL);

-- ----------------------------
-- Table structure for personal
-- ----------------------------
DROP TABLE IF EXISTS `personal`;
CREATE TABLE `personal`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `gender` int(11) NULL DEFAULT NULL COMMENT '性别 1:男 2：女',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `phone` bigint(11) NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `identity` bigint(11) NULL DEFAULT NULL COMMENT '身份证号',
  `education` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历',
  `school` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业学校',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片url',
  `work_status` int(11) NULL DEFAULT NULL COMMENT '工作状态：1:正式 2:试用 3:实习 4:离职',
  `department_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `position_id` int(11) NULL DEFAULT NULL COMMENT '职位id',
  `begin_date` datetime(0) NULL DEFAULT NULL COMMENT '入职时间',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personal
-- ----------------------------
INSERT INTO `personal` VALUES (1, '张三', 1, '1991-12-20 00:00:00', 18888888888, 'xxxx@qq.com', 430600199112200000, '本科', 'xxx大学', 'http://image.timelost.cn/ehr/1638690037555893842.jpg', 2, 1, 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for personal_reward
-- ----------------------------
DROP TABLE IF EXISTS `personal_reward`;
CREATE TABLE `personal_reward`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '奖罚ID',
  `personal_id` int(11) NULL DEFAULT NULL COMMENT '员工编号',
  `personal_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `position_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位名称',
  `reward_date` datetime(0) NULL DEFAULT NULL COMMENT '日期',
  `reward_kind` int(11) NULL DEFAULT NULL COMMENT '奖罚种类 1：奖励 2：惩罚',
  `reward_amount` int(11) NULL DEFAULT NULL COMMENT '金额',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖罚描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personal_reward
-- ----------------------------
INSERT INTO `personal_reward` VALUES (1, 1, '张三', '人事部', '人事部总管', '2021-12-01 00:00:00', 2, 50, '迟到扣除');
INSERT INTO `personal_reward` VALUES (2, 1, '张三', '人事部', '人事部总管', '2021-12-10 00:00:00', 1, 100, '绩效奖励');

-- ----------------------------
-- Table structure for personal_salary
-- ----------------------------
DROP TABLE IF EXISTS `personal_salary`;
CREATE TABLE `personal_salary`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '薪资管理ID',
  `personal_id` int(11) NULL DEFAULT NULL COMMENT '员工编号',
  `personal_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `position_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `salary_date` datetime(0) NULL DEFAULT NULL COMMENT '日期',
  `basis_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '基本工资',
  `subsidy_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '补助工资',
  `social_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '社保',
  `provident_fund` decimal(10, 0) NULL DEFAULT NULL COMMENT '公积金',
  `bonus` decimal(10, 0) NULL DEFAULT NULL COMMENT '奖金',
  `all_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '应发工资',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personal_salary
-- ----------------------------
INSERT INTO `personal_salary` VALUES (1, 1, '张三', '人事部', '人事部总管', '2021-11-30 00:00:00', 8000.00, 500.00, 1000.00, 1000, 1000, 11500.00);

-- ----------------------------
-- Table structure for personal_train
-- ----------------------------
DROP TABLE IF EXISTS `personal_train`;
CREATE TABLE `personal_train`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '培训管理ID',
  `personal_id` int(11) NULL DEFAULT NULL COMMENT '员工id',
  `personal_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `position_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `begin_date` datetime(0) NULL DEFAULT NULL COMMENT '开始培训日期',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '结束培训日期',
  `train_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培训内容',
  `train_score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培训分数',
  `train_cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用',
  `remake` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personal_train
-- ----------------------------
INSERT INTO `personal_train` VALUES (1, 1, '张三', '人事部', '人事部总管', '2021-12-01 00:00:00', '2021-12-05 00:00:00', '新人培训', '100', 1000.00, '');

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位编号',
  `position_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `department_id` int(11) NULL DEFAULT NULL COMMENT '部门编号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES (1, '人事部总管', '', 1, NULL, NULL);
INSERT INTO `position` VALUES (2, '人事职员', '负责招聘管理', 1, NULL, NULL);
INSERT INTO `position` VALUES (3, '后端开发', '', 2, NULL, NULL);
INSERT INTO `position` VALUES (4, '前端开发', '', 2, NULL, NULL);
INSERT INTO `position` VALUES (5, '移动端研发', '', 2, NULL, NULL);

-- ----------------------------
-- Table structure for recruitment
-- ----------------------------
DROP TABLE IF EXISTS `recruitment`;
CREATE TABLE `recruitment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '招聘部门',
  `position_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '招聘职位',
  `need_num` int(11) NULL DEFAULT NULL COMMENT '需求人数',
  `demand` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '招聘内容',
  `need_education` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历要求',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '发布日期',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
  `recruit_status` int(11) NULL DEFAULT NULL COMMENT '状态  1:进行中 2：已完成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recruitment
-- ----------------------------
INSERT INTO `recruitment` VALUES (1, '研发部门', '后端开发', 5, 'JAVA开发招聘', '本科', '2021-12-01 00:00:00', '2021-12-31 00:00:00', 1);
INSERT INTO `recruitment` VALUES (2, '研发部门', '前端开发', 3, '前端招聘', '本科', '2021-11-01 00:00:00', '2021-11-30 00:00:00', 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '管理员', NULL);
INSERT INTO `role` VALUES (2, 'user', '普通用户', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '2750c0b8de30d4286c0c88876ccfa795', 'LlDnqkuxHQ', 1);
INSERT INTO `user` VALUES (2, 'test', '20ca5ce0b375619db93ea45c54adb155', 'dHdgflonAX', 2);

SET FOREIGN_KEY_CHECKS = 1;
