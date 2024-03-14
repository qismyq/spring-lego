
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dic_alarm_email
-- ----------------------------
CREATE TABLE IF NOT EXISTS `dic_sms_content` (
  `id` int(2) unsigned NOT NULL DEFAULT '0',
  `verify` tinyint(1) unsigned NOT NULL COMMENT '是否校验手机号已存在 0不校验1不能存在2必须存在',
  `type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '短信类型 描述语',
  `template_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信模板ID',
  `deleted` bit(1) NOT NULL COMMENT '是否弃用 0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
