package com.springlego.autoconfigure.security.user;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Classname LegoUserDetailsService
 * @Description 多用户类型
 * @Date 2022/4/11 下午 02:05
 * @Created by michael wong
 */
public interface LegoUserDetailsService extends UserDetailsService {


    /**
     * 支持范围
     * @param range
     * @return
     */
    Boolean supports(String range);
}
