package com.springlego.autoconfigure.security.filter;

import com.springlego.autoconfigure.security.errorhandler.SecurityErrorCodeEnum;
import com.springlego.autoconfigure.security.exception.LegoAuthenticationException;
import com.springlego.autoconfigure.security.token.SmsCodeAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname SmsCodeAuthenticationFilter
 * @Description 验证码登录filter
 * @Date 2022/5/12 下午 04:52
 * @Created by michael wong
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private RedisTemplate<String, Object> redisTemplate;

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER;

    private static final String PATTERN;

    static {
        PATTERN = "/sms/login";
        DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(PATTERN,"POST");
    }
    private static final String FRONT_ROLE = "ROLE_FRONT";

    private String usernameParameter = "username";
    private String smscodeParameter = "smscode";
    private boolean postOnly = true;

    /**
     * redis验证码前缀
     */
    private String CODE_PREFIX = "CODE";


    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * 通过 传入的 参数 创建 匹配器
     * 即 Filter过滤的url
     */
    public SmsCodeAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    /**
     * 获得 用户名（手机号） 和 验证码装配到token上，然后将token交给provider进行授权
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        if (this.postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        } else {
            String username = this.obtainUsername(httpServletRequest);
            username = username != null ? username : "";
            username = username.trim();
            String smscode = this.obtainSmscode(httpServletRequest);

            if (!this.checkCode(username, smscode)) {
                throw new LegoAuthenticationException(SecurityErrorCodeEnum.TOKEN_ERROR.getErrorMessage());
            }
            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(username, smscode, AuthorityUtils.createAuthorityList(FRONT_ROLE));
            this.setDetails(httpServletRequest, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取 头部信息 让合适的provider 来验证他
     */
    public void setDetails(HttpServletRequest request , SmsCodeAuthenticationToken token ){
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 获取 传来 的username信息
     */
    public String obtainUsername(HttpServletRequest request ){
        String result=  request.getParameter(usernameParameter);
        return result;
    }
    /**
     * 获取 传来 的smscode信息
     */
    public String obtainSmscode(HttpServletRequest request ){
        String result=  request.getParameter(smscodeParameter);
        return result;
    }

    /**
     * 判断 传来的 验证码信息 以及 session 中的验证码信息
     */
    public boolean checkCode(String mobile,String smsCode ){
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsCode)){
            return false;
        }
        String redisSmsCodeKey = CODE_PREFIX + mobile;
        Object o = redisTemplate.opsForValue().get(redisSmsCodeKey);
        if(o==null){
            return false;
        }
        if(o.equals(smsCode)){
            //验证通过，删除验证码
            redisTemplate.delete(redisSmsCodeKey);
            return true;
        }
        return false;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
