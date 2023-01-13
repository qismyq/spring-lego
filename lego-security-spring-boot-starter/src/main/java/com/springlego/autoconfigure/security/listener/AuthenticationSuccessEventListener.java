package com.springlego.autoconfigure.security.listener;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 用户登录的监听事件
 *
 * @author michael wong
 */
@Component
@AllArgsConstructor
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    // todo 记录日志
//    private LogProducer logProducer;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
//        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//
//        //登录日志
//        SysLogLogin log = new SysLogLogin();
//        log.setType(LogTypeEnum.LOGIN.value());
//        log.setOperation(LoginOperationEnum.LOGIN.value());
//        log.setIp(IpUtils.getIpAddr(request));
//        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
//        log.setIp(IpUtils.getIpAddr(request));
//        log.setCreatorName(request.getParameter("username"));
//        log.setCreateDate(new Date());
//        logProducer.saveLog(log);
    }
}
