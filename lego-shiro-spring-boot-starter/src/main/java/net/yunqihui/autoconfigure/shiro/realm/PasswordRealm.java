package net.yunqihui.autoconfigure.shiro.realm;


import net.yunqihui.autoconfigure.shiro.entity.Account;
import net.yunqihui.autoconfigure.shiro.entity.ShiroStatic;
import net.yunqihui.autoconfigure.shiro.provider.AccountProvider;
import net.yunqihui.autoconfigure.shiro.token.PasswordToken;
import net.yunqihui.autoconfigure.shiro.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* *
 * @Author Michael Wong
 * @Description 这里是登录认证realm
 * @Date 14:08 2019/11/26
 * @Update Michael Wong
 * @UpdateDescription <2019-11-27>此类一定要加@Component，因为虽然通过RealmManager初始化了realm，但是并未托管给spring IOC，
 *                  导致shiroautoconfiguration会检测到@ConditionalOnMissingBean({Realm.class})
 */
@Component
public class PasswordRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordRealm.class);

    private AccountProvider accountProvider;

    //此Realm只支持PasswordToken
    public Class<?> getAuthenticationTokenClass() {
        return PasswordToken.class;
    }


    // 这里只需要认证登录，成功之后派发 json web token 授权在那里进行
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (!(authenticationToken instanceof PasswordToken)) return null;

        PasswordToken passwordToken = (PasswordToken) authenticationToken;

        if(null==passwordToken.getPrincipal()||null==passwordToken.getCredentials()){
            throw new UnknownAccountException();
        }
        String principal = (String)authenticationToken.getPrincipal();
        Account account = null;
        try {
            // login请求来源，system代表管理系统请求，app或无value代表为客户端请求
            String source = passwordToken.getSource();
            if (StringUtils.isNotBlank(source)) {
                account = accountProvider.loadAccount(principal);
            }else {

            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while accessing the account :{}",e.getMessage());
            return null;
        }
        if (account != null) {
            // 用盐对密码进行MD5加密
            passwordToken.setPassword(MD5Util.md5(passwordToken.getPassword()+ ShiroStatic.PASSWORD_MD5_SALT));
            return new SimpleAuthenticationInfo(principal,account.getPassword(),getName());
        } else {
            return new SimpleAuthenticationInfo(principal,"",getName());
        }

    }

    public void setAccountProvider(AccountProvider accountProvider) {
        this.accountProvider = accountProvider;
    }
}
