/**
 * Copyright (c) 2020 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */
package com.springlego.autoconfigure.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 认证异常类
 *
 * @author michael wong
 */
public class LegoAuthenticationException extends AuthenticationException {
    public LegoAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public LegoAuthenticationException(String msg) {
        super(msg);
    }
}
