
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
CREATE TABLE IF NOT EXISTS
  sys_oauth_client_details
  (
    client_id VARCHAR(128) NOT NULL COMMENT '客户端id',
    resource_ids VARCHAR(128) COMMENT '资源ids',
    client_secret VARCHAR(128) COMMENT '客户端密钥',
    scope VARCHAR(128) COMMENT '授权范围',
    authorized_grant_types VARCHAR(128) COMMENT '授权类型',
    web_server_redirect_uri VARCHAR(128) COMMENT '回调地址',
    authorities VARCHAR(128) COMMENT '权限标识',
    access_token_validity INT COMMENT '访问令牌有效期',
    refresh_token_validity INT COMMENT '刷新令牌有效期',
    additional_information VARCHAR(4096) COMMENT '附加信息',
    autoapprove VARCHAR(128) COMMENT '自动授权',
    PRIMARY KEY (client_id)
  )
  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 DEFAULT COLLATE=utf8mb4_general_ci COMMENT='OAuth客户端令牌';

-- ----------------------------
-- Records of dic_configuration
-- ----------------------------

INSERT IGNORE INTO `sys_oauth_client_details`
(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES
('springlego', '', '$2a$10$0GLOEiQUu/kuMMQjMKW2t.zfl4sWJgtM4YDCTYdgPlvKSLNDfd2yG', 'all', 'authorization_code,password,implicit,client_credentials,refresh_token,verify_code', null, null, 50000, null, null, 'true');



SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for u_user_account
-- ----------------------------
CREATE TABLE IF NOT EXISTS `u_user_account` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码盐',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `sex` tinyint(1) NULL DEFAULT 0 COMMENT '性别(0-默认未知,1-男,2-女)',
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `state` bit(1) NULL DEFAULT NULL COMMENT '状态(1-正常,0-冻结)',
  `mobile` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `front_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端用户id',
  `creator` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT 0 COMMENT '删除状态（0否1是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;

INSERT IGNORE INTO u_user_account (id, account, name, password, salt, avatar, sex, email, state, mobile, front_user_id, creator, create_time, updater, update_time, deleted) VALUES ('1', 'admin', 'wxy', '$2a$10$012Kx2ba5jzqr9gLlG4MX.bnQJTD9UWqF57XDo2N3.fPtLne02u/m', '1', null, false, null, true, null, null, null, null, null, null, 0);
