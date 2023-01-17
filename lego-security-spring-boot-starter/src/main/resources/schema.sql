
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

