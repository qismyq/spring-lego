package com.springlego.autoconfigure.shiro.realm;


import com.springlego.autoconfigure.shiro.matcher.PasswordMatcher;
import com.springlego.autoconfigure.shiro.token.JwtToken;
import com.springlego.autoconfigure.shiro.token.PasswordToken;
import com.springlego.autoconfigure.shiro.matcher.JwtMatcher;
import com.springlego.autoconfigure.shiro.provider.AccountProvider;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* *
 * @Author tomsun28
 * @Description realm管理器
 * @Date 17:52 2018/3/3
 * @Update Michael Wong
 */
@Component("realmManager")
public class RealmManager {

    private PasswordMatcher passwordMatcher;
    private JwtMatcher jwtMatcher;
    private AccountProvider accountProvider;

    @Autowired
    public RealmManager(AccountProvider accountProvider,PasswordMatcher passwordMatcher,JwtMatcher jwtMatcher) {
        this.accountProvider = accountProvider;
        this.passwordMatcher = passwordMatcher;
        this.jwtMatcher = jwtMatcher;
    }

    public List<Realm> initGetRealm() {
        List<Realm> realmList = new LinkedList<>();
        /*
         * password
         */
        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setAccountProvider(accountProvider);
        passwordRealm.setCredentialsMatcher(passwordMatcher);
        passwordRealm.setAuthenticationTokenClass(PasswordToken.class);
        realmList.add(passwordRealm);
        /*
         * jwt
         */
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(jwtMatcher);
        jwtRealm.setAuthenticationTokenClass(JwtToken.class);
        realmList.add(jwtRealm);
        return Collections.unmodifiableList(realmList);
    }
}
