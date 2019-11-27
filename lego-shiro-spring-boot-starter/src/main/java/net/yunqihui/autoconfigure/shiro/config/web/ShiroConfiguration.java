package net.yunqihui.autoconfigure.shiro.config.web;

import net.yunqihui.autoconfigure.shiro.filter.NoSessionSubjectFactory;
import net.yunqihui.autoconfigure.shiro.filter.ShiroFilterChainManager;
import net.yunqihui.autoconfigure.shiro.realm.AuthenticationTokenModularRealmAuthenticator;
import net.yunqihui.autoconfigure.shiro.realm.RealmManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/* *
 * @Author tomsun28
 * @Description shiro配置
 * @Date 12:41 2018/3/6
 */
@Configuration
public class ShiroConfiguration {

//    @Resource
//    AccountProvider accountProvider;
//    @Autowired
//    PasswordMatcher passwordMatcher;
//    @Autowired
//    JwtMatcher jwtMatcher;
//
//    @Bean
//    public RealmManager initRealmManager() {
//        return new RealmManager(accountProvider, passwordMatcher,jwtMatcher);
//    }



    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,ShiroFilterChainManager filterChainManager) {
        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(filterChainManager.initGetFilters());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainManager.initGetFilterChain());
        return shiroFilterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager securityManager(RealmManager realmManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(new AuthenticationTokenModularRealmAuthenticator());
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        NoSessionSubjectFactory subjectFactory = new NoSessionSubjectFactory(evaluator);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setRealms(realmManager.initGetRealm());
//        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

}
