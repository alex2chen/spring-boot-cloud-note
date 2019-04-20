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
-- ----------------------------
-- Records of T_USER
-- ----------------------------
INSERT INTO T_USER VALUES (1, 'alex', 'bd125da46da8032c642cdbc14291e90a', '2018-11-19 10:52:48', '1');
INSERT INTO T_USER VALUES (2, 'test', '87ab7e03c9e9bf60747618339d6fdff1','2018-11-19 17:20:21', '0');