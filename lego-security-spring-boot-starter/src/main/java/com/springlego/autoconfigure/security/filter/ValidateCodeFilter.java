package com.springlego.autoconfigure.security.filter;

import com.springlego.autoconfigure.security.handler.UserAuthenticationFailureHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 *
 * @author michael wong
 */
@AllArgsConstructor
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
    private final static String OAUTH_TOKEN_URL = "/oauth/token";
    private final static String FRONT = "front";
    private final static String FRONT_HEADER = "client";
//    private CaptchaService captchaService;
    private UserAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (FRONT.equals(request.getHeader(FRONT_HEADER))) {
            filterChain.doFilter(request, response);
        }else {
            if(request.getServletPath().equals(OAUTH_TOKEN_URL)
                    && request.getMethod().equalsIgnoreCase("POST")
                    && "password".equalsIgnoreCase(request.getParameter("grant_type"))) {
                try {
                    //校验验证码
                    validate(request);
                }catch (AuthenticationException e) {
                    //失败处理器
                    authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                    return;
                }
            }

            filterChain.doFilter(request, response);
        }

    }

    private void validate(HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        String captcha = request.getParameter("captcha");

        // todo 验证码验证实现

//        boolean flag = captchaService.validate(uuid, captcha);
//
//        if(!flag) {
//            throw new RenAuthenticationException(MessageUtils.getMessage(ErrorCode.CAPTCHA_ERROR));
//        }
    }
}
