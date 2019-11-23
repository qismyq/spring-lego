package net.yunqihui.autoconfigure.shiro.config.web;

import net.yunqihui.autoconfigure.frame.util.SpringUtils;
import net.yunqihui.autoconfigure.shiro.frame.shiro.ShiroDbRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Description shiro配置
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/5/24 10:20
 **/
@Configuration
public class ShiroConfig {

    @Value("${shiro.cookie.path}")
    private String cookiePath;
    @Value("${shiro.cookie.name}")
    private String cookieName;

    @Value("${shiro.loginUrl}")
    private String loginUrl ;
    @Value("${shiro.successUrl}")
    private String successUrl ;
    @Value("${shiro.unauthorizedUrl}")
    private String unauthorizedUrl;

    @Value("${shiro.filter.nosessoin.urls}")
    private String nosessionUrls;

    @Value("${shiro.filter.customFilters}")
    private String customFilters ;
    @Value("${shiro.filter.filterChainUrls}")
    private String filterChainUrls ;


    public final static String NOSESSIONCREATIONFILTER = "noSessionCreation";


    @Autowired
    private SpringUtils springUtils;


    //将自己的验证方式加入容器
    @Bean
    public ShiroDbRealm shiroDbRealm() {
        ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
        shiroDbRealm.setCredentialsMatcher(credentialsMatcher());
        return shiroDbRealm;
    }


    /**
     * @return 密码匹配器
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        return hashedCredentialsMatcher;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(AbstractCacheManager shiroCacheManager, DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroDbRealm());
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(shiroCacheManager);
        boolean httpSessionMode = securityManager.isHttpSessionMode();
        return securityManager;
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(SimpleCookie shareSessionCookie,
                                                   EnterpriseCacheSessionDAO shiroSessionDao) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);   // 会话过期时间，单位：毫秒(在无操作时开始计时)
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionDAO(shiroSessionDao);
        sessionManager.setSessionIdCookie(shareSessionCookie);
        return sessionManager;
    }


    @Bean(name = "shareSessionCookie")
    public SimpleCookie shareSessionCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setPath(cookiePath);
        simpleCookie.setName(cookieName);
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    @Bean(name = "shiroSessionDao")
    public EnterpriseCacheSessionDAO shiroSessionDao() {
        return new EnterpriseCacheSessionDAO();
    }


    /**
     * 单机
     *
     * @return
     */
    @ConditionalOnProperty(name = "shiro.model", havingValue = "0")
    @Bean(name = "shiroCacheManager")
    public AbstractCacheManager shiroMemoryCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 集群
     * TODO 待集成
     * @return
     */
//    @ConditionalOnProperty(name = "shiro.model", havingValue = "1")
//    @Bean(name = "shiroCacheManager")
//    public AbstractCacheManager shiroRedisCacheManager(@Qualifier("redissonClient") RedissonClient redissonClient) {
//        ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager();
//        cacheManager.setRedissonClient(redissonClient);
//        return cacheManager;
//    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(RealmSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        Map<String, String> filterUrls = new LinkedHashMap<>();
        // 主登录页放开
        //filterUrls.put(loginUrl, ShiroConfig.NOSESSIONCREATIONFILTER);
        // 其它不用登录的URL
        String[] urls = nosessionUrls.split(",");
        for (String url : urls) {
            filterUrls.put(url, ShiroConfig.NOSESSIONCREATIONFILTER);
        }
        String[] tmps = null;
        // 其它自定义  过滤器链中放入的url
        if(StringUtils.isNoneEmpty(filterChainUrls)){
            String[] diyUrls = filterChainUrls.split(",");
            for (String url : diyUrls) {
                url = url.replace('|', ',');
                tmps = url.split(":");
                filterUrls.put(tmps[0], tmps[1]);
                tmps = null;
            }
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterUrls);
        // 用户自定义的filter
        Map<String, Filter> filters = new LinkedHashMap<>();
        String[] diyFilters = customFilters.split(",");
        Object filterBean = null;
        for (String diyFilter : diyFilters) {
            tmps = diyFilter.split(":");
            try {
                filterBean = springUtils.getBean(tmps[1]);
                filters.put(tmps[0], (Filter) filterBean);
            } catch (NoSuchBeanDefinitionException e) {
            }

        }

        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }


    /**
     * shiro 以代理的形式，注入servlet过滤器,可以通过spring注入的形式，来代理一个filter执行
     * @return
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(proxy);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR);;
        return filterRegistrationBean;
    }


}
