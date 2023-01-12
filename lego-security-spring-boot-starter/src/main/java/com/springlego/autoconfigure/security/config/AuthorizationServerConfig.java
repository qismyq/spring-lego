/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.springlego.autoconfigure.security.config;

import com.springlego.autoconfigure.security.constant.SecurityConstant;
import com.springlego.autoconfigure.security.token.OAuthTokenEnhancer;
import com.springlego.autoconfigure.security.user.LegoUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器配置
 *
 * @author michael wong
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;
    private final LegoUserDetailsService userDetailsService;
    private final TokenStore tokenStore;
    private final WebResponseExceptionTranslator<OAuth2Exception> renWebResponseExceptionTranslator;

    /**
     * 配置客户端信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstant.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstant.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 授权码管理
     */
    @Bean
    public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE);
        //密码模式
        endpoints.authenticationManager(authenticationManager);
        //支持刷新令牌
        endpoints.userDetailsService(userDetailsService);
        //令牌管理
        endpoints.tokenStore(tokenStore);
        //令牌增强
        endpoints.tokenEnhancer(tokenEnhancer());
        //登录或者鉴权失败时的返回信息
        endpoints.exceptionTranslator(renWebResponseExceptionTranslator);
        //授权码管理，授权码存放在oauth_code表中
        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());
    }
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new OAuthTokenEnhancer();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
            .allowFormAuthenticationForClients()
            .tokenKeyAccess("permitAll()")   //匿名可访问/oauth/token_key
            .checkTokenAccess("isAuthenticated()") //认证后可访问/oauth/check_token
        ;
    }

}
