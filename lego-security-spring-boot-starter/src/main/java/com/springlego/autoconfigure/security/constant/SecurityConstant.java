package com.springlego.autoconfigure.security.constant;

/**
 * @Classname SecurityConstant
 * @Description TODO
 * @Date 2022/12/5 下午 05:34
 * @Created by H2018452
 */
public class SecurityConstant {

    /**
     * sys_oauth_client_details 表的字段，不包括client_id、client_secret
     */
    private static final String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    private static final String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
            + " from sys_oauth_client_details";

    /**
     * 默认的查询语句
     */
    public static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * 按条件client_id 查询
     */
    public static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

}
