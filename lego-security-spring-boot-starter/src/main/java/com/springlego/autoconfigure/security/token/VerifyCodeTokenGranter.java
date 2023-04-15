package com.springlego.autoconfigure.security.token;

import com.springlego.autoconfigure.security.user.service.LegoUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.List;
import java.util.Map;

/**
 * @author by michael wong
 * @Classname VerificationCodeTokenGranter
 * @Description 验证码模式
 * @Date 2023/1/16 下午 06:06
 */
public class VerifyCodeTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "verify_code";
    private static final String RANGE_PARAM = "range";

    private List<LegoUserDetailsService> legoUserDetailsServices;

    public VerifyCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                                  ClientDetailsService clientDetailsService,
                                  OAuth2RequestFactory requestFactory,
                                  List<LegoUserDetailsService> legoUserDetailsServices) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.legoUserDetailsServices = legoUserDetailsServices;
    }


    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> requestParameters = tokenRequest.getRequestParameters();
        String username = requestParameters.get("username");
        if (StringUtils.isBlank(username)) {
            throw new AccessDeniedException("get username is null ！");
        }
        String code = requestParameters.get("code");
        if (StringUtils.isBlank(code)) {
            throw new AccessDeniedException("get verify code is null ！");
        }

        UserDetails userDetails = null;
        LegoUserDetailsService userDetailsServiceSupport = null;

        String requestRange = requestParameters.get(RANGE_PARAM);
        for (LegoUserDetailsService userDetailsService : this.legoUserDetailsServices) {
            if (userDetailsService.supports(requestRange)) {
                userDetailsServiceSupport = userDetailsService;
                break;
            }
        }

        if (userDetailsServiceSupport == null) {
            throw new AccessDeniedException("Unsupported range type :" + requestRange);
        }
        userDetails = userDetailsServiceSupport.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("can not find the account");
        }

        AbstractAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, userAuth);
    }
}
