package com.springlego.autoconfigure.security.handler;

import com.alibaba.fastjson.JSON;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import com.springlego.autoconfigure.frame.errorhandler.FrameCodeEnum;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by michael wong
 * @Classname UserAuthenticationSuccessHandler
 * @Description 认证成功处理器
 * @Date 2023/12/19 上午 09:56
 */
@Component
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ReturnDatas successReturnDatas = ReturnDatas.getSuccessReturnDatas();
        response.getWriter().write(JSON.toJSONString(successReturnDatas));
    }
}
