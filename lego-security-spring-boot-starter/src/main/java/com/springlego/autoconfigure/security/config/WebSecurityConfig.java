package com.springlego.autoconfigure.security.config;

import com.springlego.autoconfigure.security.filter.LegoTokenResponseFilter;
import com.springlego.autoconfigure.security.filter.ValidateCodeFilter;
import com.springlego.autoconfigure.security.provider.LegoAuthenticationProvider;
import com.springlego.autoconfigure.security.user.service.LegoUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname WebSecurityConfig
 * @Description Spring Security配置
 * @Date 2022/10/06 下午 06:08
 * @Created by michael wong
 */
@AllArgsConstructor
@EnableWebSecurity
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final PasswordEncoder passwordEncoder;
//    @Autowired
//    private TokenStore tokenStore;

    @Autowired
    private  ApplicationContext applicationContext;

    /**
     * 注入AuthenticationManagerBuilder，用于配置认证提供者
     */
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

//
//    @Bean
//    public LegoTokenResponseFilter legoTokenResponseFilter() {
//        return new LegoTokenResponseFilter();
//    }


    /**
     * 密码模式需要
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        authenticationManagerBuilder.authenticationProvider(legoAuthenticationProvider());
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 增加验证码验证filter
        Map<String, ValidateCodeFilter> ValidateCodeFilterType = applicationContext.getBeansOfType(ValidateCodeFilter.class);
        if (ValidateCodeFilterType.size() != 0) {
            ValidateCodeFilterType.forEach((key, value) -> http.addFilterBefore(value,UsernamePasswordAuthenticationFilter.class));
        }

        http
                // 基于 token 机制，所以不需要 Session
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/authorize").authenticated()
            .anyRequest().permitAll()
            .and().csrf().disable();
        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }


//    @Bean(name="legoAuthenticationProvider")
//    @ConditionalOnBean(LegoUserDetailsService.class)
    public AuthenticationProvider legoAuthenticationProvider() {
        LegoAuthenticationProvider legoAuthenticationProvider= new LegoAuthenticationProvider();
        // 获取多个userDetailsService，包含用户自己拓展的
        Map<String, LegoUserDetailsService> ludsBeans = applicationContext.getBeansOfType(LegoUserDetailsService.class);
        List<LegoUserDetailsService> userDetailsServices = new ArrayList<>();
        ludsBeans.forEach((beanName,bean)->{
            userDetailsServices.add(bean);
        });
        legoAuthenticationProvider.setUserDetailsServices(userDetailsServices);
        legoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        legoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return legoAuthenticationProvider;
    }

}
