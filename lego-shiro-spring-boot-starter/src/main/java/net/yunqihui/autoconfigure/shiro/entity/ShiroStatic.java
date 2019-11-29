package net.yunqihui.autoconfigure.shiro.entity;

/**
 * @Description shiro starter 范围静态变量
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/28 18:02
 **/
public class ShiroStatic {

    /**
     * jwt 签发者
     **/
    public static final String JWT_ISSUER = "lego-shiro-token-server";
    public static final String JWT_SESSION = "JWT-SESSION-";

    public static String PASSWORD_MD5_SALT = "yunqi";

}
