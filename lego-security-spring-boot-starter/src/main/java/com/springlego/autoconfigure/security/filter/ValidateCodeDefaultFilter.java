package com.springlego.autoconfigure.security.filter;

import com.springlego.autoconfigure.security.exception.LegoAuthenticationException;
import com.springlego.autoconfigure.security.handler.UserAuthenticationFailureHandler;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 *
 * @author michael wong
 */
@AllArgsConstructor
@Component
public class ValidateCodeDefaultFilter extends ValidateCodeFilter {

    public static final String SMS = "sms:";

    private final static String REQUEST_RANGE = "range";
    private final static String REQUEST_GRANT_TYPE = "grant_type";

    private UserAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getRange() {
        return "default";
    }

    @Override
    public String getGrantType() {
        return "verify_code";
    }

    /**
     * 校验具体实现
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterByRange(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username = request.getParameter("username");
        String code = request.getParameter("code");
        try {
            boolean verifySuccess = verifySmsCode(username, code);
            if (verifySuccess) {
                filterChain.doFilter(request, response);
            }else{
                LegoAuthenticationException e = new LegoAuthenticationException("err code");
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            LegoAuthenticationException err = new LegoAuthenticationException(e.getMessage());
            authenticationFailureHandler.onAuthenticationFailure(request, response, err);
            return;
        }

    }

    @Override
    public boolean support(HttpServletRequest request) {
        String headerRange = request.getParameter(REQUEST_RANGE);
        String headerGrantType = request.getParameter(REQUEST_GRANT_TYPE);
        if (StringUtils.isBlank(headerRange) || StringUtils.isBlank(headerGrantType)) {
            return false;
        }
        return headerRange.equals(getRange()) && headerGrantType.equals(getGrantType());
    }

    public boolean verifySmsCode(String phone, String code) throws Exception {

        String rediskey = SMS + phone;
        if (stringRedisTemplate.hasKey(rediskey)) {
            String redisCode = stringRedisTemplate.opsForValue().get(rediskey);
            if (redisCode.equals(code)) {
                stringRedisTemplate.delete(rediskey);
                return true;
            }
        }
        return false;
    }
}
