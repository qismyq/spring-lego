//package net.yunqihui.autoconfigure.shiro.config.web;
//
//import net.yunqihui.autoconfigure.shiro.filter.JWTFilter;
//import net.yunqihui.autoconfigure.shiro.frame.shiro.ShiroDbRealm;
//import org.apache.shiro.authc.credential.CredentialsMatcher;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
//import org.apache.shiro.mgt.DefaultSubjectDAO;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//
//import javax.servlet.Filter;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * @Description shiro配置
// * @Author Michael Wong
// * @Email michael_wong@yunqihui.net
// * @Date 2019/5/24 10:20
// **/
//@Configuration
//public class ShiroConfig {
//
//
//    //将自己的验证方式加入容器
//    @Bean
//    public ShiroDbRealm shiroDbRealm() {
//        ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
//        shiroDbRealm.setCredentialsMatcher(credentialsMatcher());
//        return shiroDbRealm;
//    }
//
//    /**
//     * @return 密码匹配器
//     */
//    @Bean
//    public CredentialsMatcher credentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        return hashedCredentialsMatcher;
//    }
//
//    @Bean("securityManager")
//    public DefaultWebSecurityManager getManager(ShiroDbRealm shiroDbRealm) {
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        // 使用自己的realm
//        manager.setRealm(shiroDbRealm);
//
//        /*
//         * 关闭shiro自带的session，详情见文档
//         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
//         */
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        manager.setSubjectDAO(subjectDAO);
//
//        return manager;
//    }
//
//    @Bean("shiroFilter")
//    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
//        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
//
//        // 添加自己的过滤器并且取名为jwt
//        Map<String, Filter> filterMap = new HashMap<>();
//        filterMap.put("jwt", new JWTFilter());
//        factoryBean.setFilters(filterMap);
//
//        factoryBean.setSecurityManager(securityManager);
//        factoryBean.setUnauthorizedUrl("/401");
//
//        /*
//         * 自定义url规则
//         * http://shiro.apache.org/web.html#urls-
//         */
//        Map<String, String> filterRuleMap = new HashMap<>();
//        // 所有请求通过我们自己的JWT Filter
//        filterRuleMap.put("/**", "jwt");
//        // 访问401和404页面不通过我们的Filter
//        filterRuleMap.put("/401", "anon");
//        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
//        return factoryBean;
//    }
//
//    /**
//     * 下面的代码是添加注解支持
//     */
//    @Bean
//    @DependsOn("lifecycleBeanPostProcessor")
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
//        // https://zhuanlan.zhihu.com/p/29161098
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }
//}
