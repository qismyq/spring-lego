package com.springlego.autoconfigure.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Classname SmsCodeAuthenticationToken
 * @Description 验证码AuthenticationToken
 * @Date 2022/5/12 下午 06:26
 * @Created by michael wong
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;

    public SmsCodeAuthenticationToken(Object principal, Object credentials) {
        super((Collection) null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }
}
