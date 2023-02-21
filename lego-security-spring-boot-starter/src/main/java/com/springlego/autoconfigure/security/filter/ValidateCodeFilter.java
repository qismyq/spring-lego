package com.springlego.autoconfigure.security.filter;

import com.springlego.autoconfigure.security.handler.UserAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by H2018452
 * @Classname ValidateCodeFilter1
 * @Description 验证码filter 抽象类
 * @Date 2023/2/17 下午 05:08
 */
public abstract class ValidateCodeFilter extends OncePerRequestFilter {
    private String range ;
    private String grantType;
    private UserAuthenticationFailureHandler authenticationFailureHandler;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range){
        this.range = range;
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (support(request)) {
            doFilterByRange(request,response,filterChain);
        }else {
            filterChain.doFilter(request, response);
        }
//        //失败处理器
//        LegoAuthenticationException e = new LegoAuthenticationException("this code range type can not support this ValidateCodeFilter");
//        authenticationFailureHandler.onAuthenticationFailure(request, response, e);
//        return;
    }

    /**
     * 根据支持的range范围进行验证
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    protected abstract void doFilterByRange(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException;

    /**
     * 是否支持相关的range
     */
    public abstract boolean support(HttpServletRequest request);
}
