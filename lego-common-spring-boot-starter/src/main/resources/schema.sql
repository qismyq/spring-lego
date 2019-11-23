
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dic_alarm_email
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dic_alarm_email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email_address` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱地址',
  `receiver` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收件人名称',
  `type` int(2) NOT NULL COMMENT '1收件人 2抄送人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='警报邮箱';

-- ----------------------------
-- Table structure for dic_configuration
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dic_configuration` (
  `code` varchar(200) NOT NULL COMMENT '系统表的KEY',
  `value` varchar(20000) NOT NULL COMMENT '系统表的值',
  `descr` varchar(200) NOT NULL COMMENT '描述',
  `choose_flag` char(1) NOT NULL COMMENT '是否使用(Y是N否)',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '类型：1图片2文本3富文本编辑框4多图',
  PRIMARY KEY (`code`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Records of dic_configuration
-- ----------------------------
INSERT IGNORE INTO `dic_configuration` VALUES ('alarmEmailAccout', 'chuizikejirizhi@163.com', '警报邮件发件箱账号', 'Y', '1', '0');
INSERT IGNORE INTO `dic_configuration` VALUES ('alarmEmailAddress', 'chuizikejirizhi@163.com', '警报邮件发件箱地址', 'Y', '2', '0');
INSERT IGNORE INTO `dic_configuration` VALUES ('alarmEmailPwd', 'chuizikeji888', '警报邮箱密码', 'Y', '3', '0');
INSERT IGNORE INTO `dic_configuration` VALUES ('alarmEmailSubject', '项目警告！！！', '警报邮箱主题名称', 'Y', '4', '0');

