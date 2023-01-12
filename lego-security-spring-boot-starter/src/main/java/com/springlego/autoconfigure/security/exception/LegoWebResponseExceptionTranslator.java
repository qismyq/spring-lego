/**
 * Copyright (c) 2020 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */
package com.springlego.autoconfigure.security.exception;

import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import com.springlego.autoconfigure.security.errorhandler.SecurityErrorCodeEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * 登录或者鉴权失败时的返回信息
 *
 * @author michael wong
 */
@Component
public class LegoWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

        Exception exception = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        if (exception != null) {
            return handleOAuth2Exception(new LegoOAuth2Exception(e.getMessage(), e));
        }

        exception = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (exception != null) {
            return handleOAuth2Exception(new LegoOAuth2Exception(exception.getMessage(), exception));
        }

        exception = (InvalidGrantException) throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
        if (exception != null) {
            return handleOAuth2Exception(new LegoOAuth2Exception(exception.getMessage(), exception));
        }

        exception = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (exception != null) {
            return handleOAuth2Exception(new LegoOAuth2Exception(exception.getMessage(), exception));
        }

        exception = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (exception != null) {
            return handleOAuth2Exception((OAuth2Exception) exception);
        }

        return handleOAuth2Exception(new LegoOAuth2Exception(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CACHE_CONTROL, "no-store");
        headers.set(HttpHeaders.PRAGMA, "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set(HttpHeaders.WWW_AUTHENTICATE, String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }

        if (e instanceof ClientAuthenticationException) {
            return new ResponseEntity<>(e, headers, HttpStatus.valueOf(status));
        }

        ReturnDatas result = ReturnDatas.getErrorReturnDatas(SecurityErrorCodeEnum.ACCOUNT_PASSWORD_ERROR);


        return new ResponseEntity(result, headers, HttpStatus.OK);
    }
}
