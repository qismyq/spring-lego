/**
 * Copyright (c) 2020 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */
package com.springlego.autoconfigure.security.handler;

import com.alibaba.fastjson.JSON;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import com.springlego.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证失败处理器
 *
 * @author michael wong
 */
@Component
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)  {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new ReturnDatas(FrameErrorCodeEnum.E_1.getErrorCode(),e.getMessage())));
    }
}
