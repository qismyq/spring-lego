package net.yunqihui.autoconfigure.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description JWTToken
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/23 18:34
 **/
public class JWTToken extends UsernamePasswordToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public char[] getPassword() {
        return super.getPassword();
    }
}
