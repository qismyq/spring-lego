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
