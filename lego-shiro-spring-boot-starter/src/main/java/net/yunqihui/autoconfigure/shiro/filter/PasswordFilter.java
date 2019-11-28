package net.yunqihui.autoconfigure.shiro.filter;


import com.alibaba.fastjson.JSON;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
import net.yunqihui.autoconfigure.shiro.errorhandler.ShiroErrorCodeEnum;
import net.yunqihui.autoconfigure.shiro.token.PasswordToken;
import net.yunqihui.autoconfigure.shiro.util.IpUtil;
import net.yunqihui.autoconfigure.shiro.util.RequestResponseUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/* *
 * @Author tomsun28
 * @Description 基于 用户名密码 的认证过滤器
 * @Date 20:18 2018/2/10
 */
public class PasswordFilter extends AccessControlFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordFilter.class);

    private StringRedisTemplate redisTemplate;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        Subject subject = getSubject(request,response);
        // 如果其已经登录，再此发送登录请求
        if(null != subject && subject.isAuthenticated()){
            return true;
        }
        //  拒绝，统一交给 onAccessDenied 处理
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        // 判断是否是登录请求
        if(isPasswordLoginPost(request)){
            AuthenticationToken authenticationToken = createPasswordToken(request);
            Subject subject = getSubject(request,response);
            try {
                subject.login(authenticationToken);
                //登录认证成功,进入请求派发json web token url资源内
                return true;
            }catch (AuthenticationException e) {
                LOGGER.warn(authenticationToken.getPrincipal()+"::"+e.getMessage());
                // 返回response告诉客户端认证失败
                ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40201);
                RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
                return false;
            }catch (Exception e) {
                LOGGER.error(authenticationToken.getPrincipal()+"::认证异常::"+e.getMessage(),e);
                // 返回response告诉客户端认证失败
                ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(FrameErrorCodeEnum.E_50000);
                RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
                return false;
            }
        }
        // 判断是否为注册请求,若是通过过滤链进入controller注册
        if (isAccountRegisterPost(request)) {
            return true;
        }
        // 之后添加对账户的找回等

        // response 告知无效请求
        ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40200);
        RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
        return false;
    }

    private boolean isPasswordTokenGet(ServletRequest request) {

        String tokenKey = RequestResponseUtil.getParameter(request,"tokenKey");

        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("GET")
                && null != tokenKey && "get".equals(tokenKey);
    }

    private boolean isPasswordLoginPost(ServletRequest request) {

        String password = request.getParameter("password");
        String timestamp = request.getParameter("timestamp");
        String methodName = request.getParameter("methodName");
        String appId = request.getParameter("appId");
        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("POST")
                && null != password
                && null != timestamp
                && null != methodName
                && null != appId
                && methodName.equals("login");
    }

    private boolean isAccountRegisterPost(ServletRequest request) {

        Map<String ,String> map = RequestResponseUtil.getRequestBodyMap(request);
        String uid = map.get("uid");
        String username = map.get("username");
        String methodName = map.get("methodName");
        String password = map.get("password");
        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest) request).getMethod().toUpperCase().equals("POST")
                && null != username
                && null != password
                && null != methodName
                && null != uid
                && methodName.equals("register");
    }

    private AuthenticationToken createPasswordToken(ServletRequest request) {

        Map<String ,String> map = RequestResponseUtil.getRequestParameters(request);
        String appId = map.get("appId");
        String timestamp = map.get("timestamp");
        String password = map.get("password");
        String host = IpUtil.getIpFromRequest(WebUtils.toHttp(request));
        String userKey = map.get("userKey");
        String tokenKey = redisTemplate.opsForValue().get("TOKEN_KEY_"+host.toUpperCase()+userKey);
        return new PasswordToken(appId,password,timestamp,host,tokenKey);
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
