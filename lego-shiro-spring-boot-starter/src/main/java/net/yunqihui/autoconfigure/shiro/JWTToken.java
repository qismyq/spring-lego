//package net.yunqihui.autoconfigure.shiro;
//
//import org.apache.shiro.authc.AuthenticationToken;
//
///**
// * @Description JWTToken
// * @Author Michael Wong
// * @Email michael_wong@yunqihui.net
// * @Date 2019/11/23 18:34
// **/
//public class JWTToken implements AuthenticationToken {
//    private static final long serialVersionUID = 1L;
//    // 密钥
//    private String token;
//
////    public JWTToken(final String username,final String password,String token) {
////        this.token = token;
////    }
//
//    public JWTToken(String token) {
//        this.token = token;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return token;
//    }
//
////    @Override
////    public Object getCredentials() {
////        return super.getCredentials();
////    }
//
//    @Override
//    public Object getCredentials() {
//        return token;
//    }
//
//}
