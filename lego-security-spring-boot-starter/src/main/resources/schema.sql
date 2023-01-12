
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
