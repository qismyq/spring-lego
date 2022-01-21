package com.springlego.autoconfigure.shiro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.springlego.autoconfigure.shiro.filter.NoSessionSubjectFactory;
import com.springlego.autoconfigure.shiro.realm.AuthenticationTokenModularRealmAuthenticator;
import com.springlego.autoconfigure.shiro.realm.RealmManager;
import com.springlego.autoconfigure.shiro.support.XssSqlStringJsonSerializer;
import com.springlego.autoconfigure.shiro.filter.ShiroFilterChainManager;
import com.springlego.autoconfigure.shiro.support.XssSqlFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


/* *
 * @Author tomsun28
 * @Description shiro配置
 * @Date 12:41 2018/3/6
 * @Update Michael Wong
 */
@Configuration
public class ShiroConfiguration {


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
        return securityManager;
    }

    /**
     * @desc: xsssql过滤器配置，不使用@webfilter配置，防止封装为starter后引用者没有在主启动类上声明@ServletComponentScan
     * @return: org.springframework.boot.web.servlet.FilterRegistrationBean
     * @auther: Michael Wong
     * @email:  michael_wang90@163.com
     * @date:   2019/11/29 11:06
     */
    @Bean
    public FilterRegistrationBean xssSqlFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new XssSqlFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("xssFilter");
        registrationBean.setOrder(1);
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }

    @SuppressWarnings("unchecked")
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    @Primary
    public ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
        // 解析器
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // 注册XSS SQL 解析器
        SimpleModule xssModule = new SimpleModule("XssStringJsonSerializer");
        xssModule.addSerializer(new XssSqlStringJsonSerializer());
        objectMapper.registerModule(xssModule);
        return objectMapper;
    }

}
