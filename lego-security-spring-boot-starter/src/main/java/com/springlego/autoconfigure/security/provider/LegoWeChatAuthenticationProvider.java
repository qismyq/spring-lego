//package com.springlego.autoconfigure.security.provider;
//
//import com.springlego.autoconfigure.security.token.WeChatAuthenticationToken;
//import com.springlego.autoconfigure.security.user.LegoUserDetailsService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Map;
//
///**
// * @Classname LegoSmsAuthenticationProvider
// * @Description 验证码provider
// * @Date 2022/5/12 下午 05:30
// * @Created by michael wong
// */
//@Slf4j
//public class LegoWeChatAuthenticationProvider implements AuthenticationProvider {
//
//    private LegoUserDetailsService userDetailsService;
//
//
//    /**
//     * 验证
//     * @param authentication
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        if (!supports(authentication.getClass())) {
//            return null;
//        }
//        log.info("SmsCodeAuthenticationToken authentication request: %s", authentication);
//        WeChatAuthenticationToken token = (WeChatAuthenticationToken) authentication;
//        UserDetails loadedUser = null;
//
//        Map<String,String> map = (Map<String, String>) token.getPrincipal();
//
//        try {
//            loadedUser = this.getUserDetailsService().loadUserByOpenId(map);
//            if (loadedUser == null) {
//                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
//            }
//        }
//        // TODO 暂时不处理UsernameNotFoundException的特殊情况
//        /*catch (ErrorMessageException eme){
//            if (eme.getErrorCode() == ErrorCode.ACCOUNT_NOT_EXIST) {
//                throw new UsernameNotFoundException(eme.getMessage());
//            }
//        }*/catch (InternalAuthenticationServiceException var5) {
//            throw var5;
//        } catch (Exception var6) {
//            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
//        }
//
//        WeChatAuthenticationToken result = new WeChatAuthenticationToken(loadedUser, token.getCredentials(),loadedUser.getAuthorities());
//        /*
//         * Details 中包含了 ip地址、 sessionId 等等属性 也可以存储一些自己想要放进去的内容
//        */
//        result.setDetails(token.getDetails());
//        return result;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return WeChatAuthenticationToken.class.isAssignableFrom(aClass);
//    }
//
//    public void setUserDetailsService(LegoUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    protected LegoUserDetailsService getUserDetailsService() {
//        return this.userDetailsService;
//    }
//}
