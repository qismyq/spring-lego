/**
 * Copyright (c) 2020 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.springlego.autoconfigure.security.config;

import com.springlego.autoconfigure.security.filter.SmsCodeAuthenticationFilter;
import com.springlego.autoconfigure.security.filter.ValidateCodeFilter;
import com.springlego.autoconfigure.security.filter.WeChatAuthenticationFilter;
import com.springlego.autoconfigure.security.handler.SmsAuthenticationSuccessHandler;
import com.springlego.autoconfigure.security.handler.UserAuthenticationFailureHandler;
import com.springlego.autoconfigure.security.handler.WeChatAuthenticationSuccessHandler;
import com.springlego.autoconfigure.security.provider.LegoAuthenticationProvider;
import com.springlego.autoconfigure.security.provider.LegoSmsAuthenticationProvider;
import com.springlego.autoconfigure.security.provider.LegoWeChatAuthenticationProvider;
import com.springlego.autoconfigure.security.user.LegoUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Classname WebSecurityConfig
 * @Description Spring Security配置
 * @Date 2022/10/06 下午 06:08
 * @Created by michael wong
 */
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final LegoUserDetailsService userDetailsService;
//    private final UserDetailsService userDetailsService;
    private final ValidateCodeFilter validateCodeFilter;
    private final SmsAuthenticationSuccessHandler smsAuthenticationSuccessHandler;
    private final WeChatAuthenticationSuccessHandler weChatAuthenticationSuccessHandler;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final UserAuthenticationFailureHandler userAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(legoAuthenticationProvider());
        auth.authenticationProvider(legoSmsAuthenticationProvider());
        auth.authenticationProvider(legoWechatAuthenticationProvider());
    }

    /**
     * 密码模式需要
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//            .addFilterBefore(smsCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/authorize").authenticated()
            .anyRequest().permitAll()
            .and().csrf().disable()
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    @Bean(name="legoAuthenticationProvider")
    public AuthenticationProvider legoAuthenticationProvider() {
        LegoAuthenticationProvider legoAuthenticationProvider= new LegoAuthenticationProvider();
        legoAuthenticationProvider.setUserDetailsService(userDetailsService);
        legoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        legoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return legoAuthenticationProvider;
    }

    @Bean(name="legoSmsAuthenticationProvider")
    public AuthenticationProvider legoSmsAuthenticationProvider() {
        LegoSmsAuthenticationProvider legoSmsAuthenticationProvider= new LegoSmsAuthenticationProvider();
        legoSmsAuthenticationProvider.setUserDetailsService(userDetailsService);
        return legoSmsAuthenticationProvider;
    }

    @Bean(name="legoWechatAuthenticationProvider")
    public AuthenticationProvider legoWechatAuthenticationProvider() {
        LegoWeChatAuthenticationProvider legoWeChatAuthenticationProvider= new LegoWeChatAuthenticationProvider();
        legoWeChatAuthenticationProvider.setUserDetailsService(userDetailsService);
        return legoWeChatAuthenticationProvider;
    }

    @Bean
    public SmsCodeAuthenticationFilter smsCodeAuthenticationFilter() throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        try {
            smsCodeAuthenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(smsAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(userAuthenticationFailureHandler);
        smsCodeAuthenticationFilter.setRedisTemplate(redisTemplate);
        return smsCodeAuthenticationFilter;
    }

    @Bean
    public WeChatAuthenticationFilter WeChatAuthenticationFilter() throws Exception {
        WeChatAuthenticationFilter weChatAuthenticationFilter = new WeChatAuthenticationFilter();
        try {
            weChatAuthenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        weChatAuthenticationFilter.setAuthenticationSuccessHandler(weChatAuthenticationSuccessHandler);
        weChatAuthenticationFilter.setAuthenticationFailureHandler(userAuthenticationFailureHandler);
        return weChatAuthenticationFilter;
    }
}
