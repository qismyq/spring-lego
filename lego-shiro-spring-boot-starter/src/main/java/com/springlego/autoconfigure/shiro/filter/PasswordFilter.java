//package com.springlego.autoconfigure.shiro.filter;
//
//
//import com.alibaba.fastjson.JSON;
//import com.springlego.autoconfigure.frame.entity.ReturnDatas;
//import com.springlego.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
//import ShiroErrorCodeEnum;
//import PasswordToken;
//import IpUtil;
//import RequestResponseUtil;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
///* *
// * @Author tomsun28
// * @Description 基于 用户名密码 的认证过滤器
// * @Date 20:18 2018/2/10
// */
//public class PasswordFilter extends AccessControlFilter {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordFilter.class);
//
//    private StringRedisTemplate redisTemplate;
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//
//        Subject subject = getSubject(request,response);
//        // 如果其已经登录，再此发送登录请求
//        if(null != subject && subject.isAuthenticated()){
//            return true;
//        }
//        //  拒绝，统一交给 onAccessDenied 处理
//        return false;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//
//        AuthenticationToken authenticationToken = createPasswordToken(request);
//        Subject subject = getSubject(request,response);
//        try {
//            subject.login(authenticationToken);
//            //登录认证成功,进入请求派发json web token url资源内
//            return true;
//        }catch (AuthenticationException e) {
//            LOGGER.warn(authenticationToken.getPrincipal()+"::"+e.getMessage());
//            // 返回response告诉客户端认证失败
//            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40201);
//            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
//            return false;
//        }catch (Exception e) {
//            LOGGER.error(authenticationToken.getPrincipal()+"::认证异常::"+e.getMessage(),e);
//            // 返回response告诉客户端认证失败
//            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(FrameErrorCodeEnum.E_50000);
//            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
//            return false;
//        }
//    }
//
//    private boolean isPasswordTokenGet(ServletRequest request) {
//
//        String tokenKey = RequestResponseUtil.getParameter(request,"tokenKey");
//
//        return (request instanceof HttpServletRequest)
//                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("GET")
//                && null != tokenKey && "get".equals(tokenKey);
//    }
//
//    private boolean isPasswordLoginPost(ServletRequest request) {
//
//        String password = request.getParameter("password");
//        String timestamp = request.getParameter("timestamp");
//        String methodName = request.getParameter("methodName");
//        String appId = request.getParameter("appId");
//        return (request instanceof HttpServletRequest)
//                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("POST")
//                && null != password
//                && null != timestamp
//                && null != methodName
//                && null != appId
//                && methodName.equals("login");
//    }
//
//    private boolean isAccountRegisterPost(ServletRequest request) {
//
//        Map<String ,String> map = RequestResponseUtil.getRequestBodyMap(request);
//        String uid = map.get("uid");
//        String username = map.get("username");
//        String methodName = map.get("methodName");
//        String password = map.get("password");
//        return (request instanceof HttpServletRequest)
//                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("POST")
//                && null != username
//                && null != password
//                && null != methodName
//                && null != uid
//                && methodName.equals("register");
//    }
//
//    private AuthenticationToken createPasswordToken(ServletRequest request) {
//
////        Map<String ,String> map = RequestResponseUtil.getRequestParameters(request);
//        Map<String, String> map = RequestResponseUtil.getRequestBodyMap(request);
////        String appId = RequestResponseUtil.getParameter(request, "account");
////        String timestamp = RequestResponseUtil.getParameter(request, "timestamp");
////        String password = RequestResponseUtil.getParameter(request, "password");
//        String appId = map.get("account");
//        String timestamp = map.get("timestamp");
//        String password = map.get("password");
//        String source = RequestResponseUtil.getRequestHeaders(request).get("source");
//        String host = IpUtil.getIpFromRequest(WebUtils.toHttp(request));
//        return new PasswordToken(appId,password,timestamp,host,source);
//    }
//
//    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//}
