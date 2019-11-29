package net.yunqihui.autoconfigure.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/* *
 * @Author tomsun28
 * @Description 
 * @Date 12:34 2018/2/27
 */
public class PasswordToken implements AuthenticationToken{

    private static final long serialVersionUID = 1L;
    private String appId;
    private String password;
    private String timestamp;
    private String host;
    private String source;

    public PasswordToken(String appId, String password, String timestamp, String host,String source) {
        this.appId = appId;
        this.timestamp = timestamp;
        this.host = host;
        this.password = password;
        this.source = source;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }

    public Object getPrincipal() {
        return this.appId;
    }

    public Object getCredentials() {
        return this.password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
