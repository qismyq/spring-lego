package com.springlego.autoconfigure.security.filter;

import com.springlego.autoconfigure.security.token.WeChatAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname SmsCodeAuthenticationFilter
 * @Description 验证码登录filter
 * @Date 2022/5/12 下午 04:52
 * @Created by michael wong
 */
public class WeChatAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER;

    private static final String PATTERN;

    static {
        PATTERN = "/wechat/login";
        DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(PATTERN,"POST");
    }
    private static final String FRONT_ROLE = "ROLE_FRONT";

    private String openIdParameter = "openId";
    private String usernameParameter = "username";
    private String headerParameter = "header";
    private String sexParameter = "sex";
    private boolean postOnly = true;

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * 通过 传入的 参数 创建 匹配器
     * 即 Filter过滤的url
     */
    public WeChatAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    /**
     * 获得 用户名（手机号） 和 验证码装配到token上，然后将token交给provider进行授权
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        if (this.postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        } else {
            String openId = this.obtainOpenId(httpServletRequest);
            openId = openId != null ? openId : "";
            openId = openId.trim();

            String username = this.obtainUsername(httpServletRequest);
            username = username != null ? username : "";
            username = username.trim();

            String header = this.obtainHeader(httpServletRequest);
            header = header != null ? header : "";
            header = header.trim();

            String sex = this.obtainSex(httpServletRequest);
            sex = sex != null ? sex : "";
            sex = sex.trim();

            Map<String,String> map = new HashMap<>();
            map.put("openId",openId);
            map.put("username",username);
            map.put("header",header);
            map.put("sex",sex);
            WeChatAuthenticationToken authRequest = new WeChatAuthenticationToken(map,null, AuthorityUtils.createAuthorityList(FRONT_ROLE));
            this.setDetails(httpServletRequest, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取 头部信息 让合适的provider 来验证他
     */
    public void setDetails(HttpServletRequest request , WeChatAuthenticationToken token ){
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 获取 传来 的openid信息
     */
    public String obtainOpenId(HttpServletRequest request ){
        String result=  request.getParameter(openIdParameter);
        return result;
    }

    /**
     * 获取 传来 的username信息
     */
    public String obtainUsername(HttpServletRequest request ){
        String result=  request.getParameter(usernameParameter);
        return result;
    }

    /**
     * 获取 传来 的header信息
     */
    public String obtainHeader(HttpServletRequest request ){
        String result=  request.getParameter(headerParameter);
        return result;
    }

    /**
     * 获取 传来 的sex信息
     */
    public String obtainSex(HttpServletRequest request ){
        String result=  request.getParameter(sexParameter);
        return result;
    }

}
