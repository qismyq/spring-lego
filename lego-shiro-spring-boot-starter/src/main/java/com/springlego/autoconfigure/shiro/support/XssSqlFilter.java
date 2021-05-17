package com.springlego.autoconfigure.shiro.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/* *
 * @Author tomsun28
 * @Description 
 * @Date 21:24 2018/4/16
 * @Update Michael Wong
 */
public class XssSqlFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(XssSqlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("xssFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssSqlHttpServletRequestWrapper xssSqlHttpServletRequestWrapper = new XssSqlHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(xssSqlHttpServletRequestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.debug("xssFilter destroy");
    }
}
