/**
 * Copyright (c) 2020 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */
package com.springlego.autoconfigure.security.constant;

/**
 * 资源常量
 *
 * @author michael wong
 */
public class ResourceConstant {
    /**
     * 不进行认证的URL
     */
    public static final String [] IGNORING_URLS = {
        "/actuator/**",
        "/v2/api-docs",
        "/webjars/**",
        "/swagger/**",
        "/swagger-resources/**",
        "/doc.html",
        "/sms/send",
        "/register",
        "/file/upload",
        "/sms/validateCode",
        "/user/resetPwd"
    };

}
