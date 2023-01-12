package com.springlego.autoconfigure.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springlego.autoconfigure.frame.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * @Classname SmsAuthenticationSuccessHandler
 * @Description 验证码登录成功处理
 * @Date 2022/5/12 下午 08:12
 * @Created by michael wong
 */
@Slf4j
@Component
public class WeChatAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler /*AuthenticationSuccessHandler*/ {
    @Autowired
    private ObjectMapper objectMapper;

    // 不能自动注入，会产生循环依赖
    private AuthorizationServerTokenServices authorizationServerTokenServices = null;
    @Autowired
    private ClientDetailsService clientDetailsService;


    //    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    public PasswordEncoder passwordEncoder;


    //登录成功之后会被调用
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");

        String header = request.getHeader("Authorization");
        if (header == null && !header.startsWith("Basic")) {
            throw new UnapprovedClientAuthenticationException("请求投中无client信息");
        }
        String tmp = header.substring(6);
        String defaultClientDetails = new String(Base64.getDecoder().decode(tmp));

        String[] clientArrays = defaultClientDetails.split(":");
        String clientId = clientArrays[0].trim();
        String clientSecret = clientArrays[1].trim();
//
//
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId 不存在" + clientId);
            //判断  方言  是否一致
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配" + clientId);
        }
        TokenRequest tokenRequest = new TokenRequest(null, clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        if (authorizationServerTokenServices == null) {
            authorizationServerTokenServices = SpringContextHolder.getBeanByType(AuthorizationServerTokenServices.class);
        }


        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        JSONObject result = new JSONObject();
        result.put("code",token.getAdditionalInformation().get("code"));
        result.put("token_type",token.getTokenType());
        result.put("access_token",token.getValue());
        result.put("expires_in",token.getExpiresIn());
        result.put("refresh_token",token.getRefreshToken().getValue());
        result.put("scope",token.getScope().iterator().next());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(result));


    }

}
