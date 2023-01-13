package com.springlego.autoconfigure.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

/**
 * @Classname LegoUserDetailsService
 * @Description 多用户类型
 * @Date 2022/4/11 下午 02:05
 * @Created by michael wong
 */
public interface LegoUserDetailsService extends UserDetailsService {

    /**
     * 多用户类型刷新token
     * @param username 用户名
     * @param userType 用户类型
     * @return
     */
    UserDetails loadUserByUsername(String username, String userType)throws UsernameNotFoundException;

    /**
     *
     * @param params
     * @return
     */
    UserDetail loadUserByOpenId(Map<String, String> params) throws UsernameNotFoundException;

    /**
     * 支持范围
     * @param range
     * @return
     */
    Boolean supports(String range);
}
