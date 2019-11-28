package net.yunqihui.autoconfigure.shiro.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.SignatureAlgorithm;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.shiro.entity.vo.ShiroStatic;
import net.yunqihui.autoconfigure.shiro.errorhandler.ShiroErrorCodeEnum;
import net.yunqihui.autoconfigure.shiro.provider.AccountProvider;
import net.yunqihui.autoconfigure.shiro.token.JwtToken;
import net.yunqihui.autoconfigure.shiro.util.IpUtil;
import net.yunqihui.autoconfigure.shiro.util.JsonWebTokenUtil;
import net.yunqihui.autoconfigure.shiro.util.RequestResponseUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/* *
 * @Author tomsun28
 * @Description 支持restful url 的过滤链  JWT json web token 过滤器，无状态验证
 * @Date 0:04 2018/4/20
 */
public class JWTFilter extends RestPathMatchingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);


    private StringRedisTemplate redisTemplate;
    private AccountProvider accountProvider;

    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);


        // 判断是否为JWT认证请求
        if ((null != subject && !subject.isAuthenticated()) && isJwtSubmission(servletRequest)) {
            AuthenticationToken token = createJwtToken(servletRequest);
            try {
                subject.login(token);
                return this.checkRoles(subject,mappedValue);
            }catch (AuthenticationException e) {

                // 如果是JWT过期
                if ("expiredJwt".equals(e.getMessage())) {
                    // 这里初始方案先抛出令牌过期，之后设计为在Redis中查询当前appId对应令牌，其设置的过期时间是JWT的两倍，此作为JWT的refresh时间
                    // 当JWT的有效时间过期后，查询其refresh时间，refresh时间有效即重新派发新的JWT给客户端，
                    // refresh也过期则告知客户端JWT时间过期重新认证

                    // 当存储在redis的JWT没有过期，即refresh time 没有过期
                    String appId = WebUtils.toHttp(servletRequest).getHeader("appId");
                    String jwt = WebUtils.toHttp(servletRequest).getHeader("authorization");
                    String refreshJwt = redisTemplate.opsForValue().get(ShiroStatic.JWT_SESSION+appId);
                    if (null != refreshJwt && refreshJwt.equals(jwt)) {
                        // 重新申请新的JWT
                        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
                        String roles = accountProvider.loadAccountRole(appId);
                        long refreshPeriodTime = 36000L;  //seconds为单位,10 hours
                        String newJwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(),appId,
                                ShiroStatic.JWT_ISSUER,refreshPeriodTime >> 2,roles,null, SignatureAlgorithm.HS512);
                        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
                        redisTemplate.opsForValue().set(ShiroStatic.JWT_SESSION+appId,newJwt,refreshPeriodTime, TimeUnit.SECONDS);
                        // todo 刷新token的返回消息提示
//                        Message message = new Message().ok(1005,"new jwt").addData("jwt",newJwt);
//                        RequestResponseUtil.responseWrite(JSON.toJSONString(message),servletResponse);
                        return false;
                    }else {
                        // jwt时间失效过期,jwt refresh time失效 返回jwt过期客户端重新登录
                        ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40204);
                        RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),servletResponse);
                        return false;
                    }

                }
                // 其他的判断为JWT错误无效
                ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40205);
                RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),servletResponse);
                return false;

            }catch (Exception e) {
                // 其他错误
                LOGGER.error(IpUtil.getIpFromRequest(WebUtils.toHttp(servletRequest))+"--JWT认证失败"+e.getMessage(),e);
                // 告知客户端JWT错误1005,需重新登录申请jwt
                ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_50200);
                RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),servletResponse);
                return false;
            }
        }else {
            // 请求未携带jwt 判断为无效请求
            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40206);
            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),servletResponse);
            return false;
        }
    }

    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);

        // 未认证的情况
        if (null == subject || !subject.isAuthenticated()) {
            // 告知客户端JWT认证失败需跳转到登录页面
            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40205);
            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),servletResponse);
        }else {
            //  已经认证但未授权的情况
            // 告知客户端JWT没有权限访问此资源
            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40203);
            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),servletResponse);
        }
        // 过滤链终止
        return false;
    }

    private boolean isJwtSubmission(ServletRequest request) {

        String jwt = RequestResponseUtil.getHeader(request,"authorization");
        String appId = RequestResponseUtil.getHeader(request,"appId");
        return (request instanceof HttpServletRequest)
                && !StringUtils.isEmpty(jwt)
                && !StringUtils.isEmpty(appId);
    }

    private AuthenticationToken createJwtToken(ServletRequest request) {

        Map<String,String> maps = RequestResponseUtil.getRequestHeaders(request);
        String appId = maps.get("appId");
        String ipHost = request.getRemoteAddr();
        String jwt = maps.get("authorization");
        String deviceInfo = maps.get("deviceInfo");

        return new JwtToken(ipHost,deviceInfo,jwt,appId);
    }

    // 验证当前用户是否属于mappedValue任意一个角色
    private boolean checkRoles(Subject subject, Object mappedValue){
        String[] rolesArray = (String[]) mappedValue;
        return rolesArray == null || rolesArray.length == 0 || Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }


    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void setAccountService(AccountProvider accountProvider) {
        this.accountProvider = accountProvider;
    }
}
