-- ----------------------------
-- Table structure for T_USER
-- ----------------------------
CREATE TABLE `T_USER` (
	`ID` INT(11) NOT NULL COMMENT 'ID',
	`USERNAME` VARCHAR(20) NULL DEFAULT NULL COMMENT '用户名',
	`PASSWD` VARCHAR(128) NULL DEFAULT NULL COMMENT '密码',
	`CREATE_TIME` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`STATUS` CHAR(1) NULL DEFAULT NULL COMMENT '是否有效 1：有效  0：锁定',
	PRIMARY KEY (`id`)
)COLLATE='utf8_general_ci' ENGINE=InnoDB;
INSERT INTO T_USER VALUES (1, 'alex', 'bd125da46da8032c642cdbc14291e90a', '2018-11-19 10:52:48', '1');
INSERT INTO T_USER VALUES (2, 'test', '87ab7e03c9e9bf60747618339d6fdff1','2018-11-19 17:20:21', '0');
-- ----------------------------
-- Table structure for T_PERMISSION
-- ----------------------------
CREATE TABLE `T_PERMISSION` (
	`ID` INT(11) NOT NULL COMMENT 'ID',
	`URL` VARCHAR(256) NULL DEFAULT NULL COMMENT 'url地址',
	`NAME` VARCHAR(64) NULL DEFAULT NULL COMMENT 'url描述',
	PRIMARY KEY (`id`)
)COLLATE='utf8_general_ci' ENGINE=InnoDB;
INSERT INTO T_PERMISSION VALUES (1, '/user', 'user:user');
INSERT INTO T_PERMISSION VALUES (2, '/user/add', 'user:add');
INSERT INTO T_PERMISSION VALUES (3, '/user/delete', 'user:delete');
-- ----------------------------
-- Table structure for T_ROLE
-- ----------------------------
CREATE TABLE `T_ROLE` (
	`ID` INT(11) NOT NULL COMMENT 'ID',
	`NAME` VARCHAR(32) NULL DEFAULT NULL COMMENT '角色名称',
	`MEMO` VARCHAR(32) NULL DEFAULT NULL COMMENT '角色描述',
	PRIMARY KEY (`id`)
)COLLATE='utf8_general_ci' ENGINE=InnoDB;
INSERT INTO T_ROLE VALUES (1, 'admin', '超级管理员');
INSERT INTO T_ROLE VALUES (2, 'test', '测试账户');
-- ----------------------------
-- Table structure for T_ROLE_PERMISSION
-- ----------------------------
CREATE TABLE `T_ROLE_PERMISSION` (
	`RID` INT(11) NOT NULL COMMENT '角色id',
	`PID` INT(11) NOT NULL COMMENT '权限id'
)COLLATE='utf8_general_ci' ENGINE=InnoDB;
INSERT INTO T_ROLE_PERMISSION VALUES (1, 2);
INSERT INTO T_ROLE_PERMISSION VALUES (1, 3);
INSERT INTO T_ROLE_PERMISSION VALUES (2, 1);
INSERT INTO T_ROLE_PERMISSION VALUES (1, 1);
-- ----------------------------
-- Table structure for T_USER_ROLE
-- ----------------------------
CREATE TABLE `T_USER_ROLE` (
	`USER_ID` INT(11) NOT NULL COMMENT '用户id',
	`RID` INT(11) NOT NULL COMMENT '角色id'
)COLLATE='utf8_general_ci' ENGINE=InnoDB;
INSERT INTO T_USER_ROLE VALUES (1, 1);
INSERT INTO T_USER_ROLE VALUES (2, 2);