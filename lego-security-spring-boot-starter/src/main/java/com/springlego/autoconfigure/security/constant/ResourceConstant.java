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
