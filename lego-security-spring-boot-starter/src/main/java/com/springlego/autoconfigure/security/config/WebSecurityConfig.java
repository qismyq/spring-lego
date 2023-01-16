package com.springlego.autoconfigure.security.config;

import com.springlego.autoconfigure.security.filter.ValidateCodeFilter;
import com.springlego.autoconfigure.security.handler.UserAuthenticationFailureHandler;
import com.springlego.autoconfigure.security.provider.LegoAuthenticationProvider;
import com.springlego.autoconfigure.security.user.LegoUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter /*implements ApplicationContextAware */{
    private final LegoUserDetailsService userDetailsService;
    private final ValidateCodeFilter validateCodeFilter;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final UserAuthenticationFailureHandler userAuthenticationFailureHandler;

    @Autowired
    private  ApplicationContext applicationContext;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(legoAuthenticationProvider());
//        auth.authenticationProvider(legoSmsAuthenticationProvider());
//        auth.authenticationProvider(legoWechatAuthenticationProvider());
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
    @ConditionalOnBean(LegoUserDetailsService.class)
    public AuthenticationProvider legoAuthenticationProvider() {
        LegoAuthenticationProvider legoAuthenticationProvider= new LegoAuthenticationProvider();
        // 获取多个userDetailsService
        Map<String, LegoUserDetailsService> ludsBeans = applicationContext.getBeansOfType(LegoUserDetailsService.class);
        List<LegoUserDetailsService> userDetailsServices = new ArrayList<>();
        // todo 去掉默认提供的uds
        ludsBeans.forEach((beanName,bean)->{
            userDetailsServices.add(bean);
        });
        legoAuthenticationProvider.setUserDetailsServices(userDetailsServices);
        legoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        legoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return legoAuthenticationProvider;
    }

//    @Bean(name="legoSmsAuthenticationProvider")
//    public AuthenticationProvider legoSmsAuthenticationProvider() {
//        LegoSmsAuthenticationProvider legoSmsAuthenticationProvider= new LegoSmsAuthenticationProvider();
//        legoSmsAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return legoSmsAuthenticationProvider;
//    }
//
//    @Bean(name="legoWechatAuthenticationProvider")
//    public AuthenticationProvider legoWechatAuthenticationProvider() {
//        LegoWeChatAuthenticationProvider legoWeChatAuthenticationProvider= new LegoWeChatAuthenticationProvider();
//        legoWeChatAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return legoWeChatAuthenticationProvider;
//    }

//    @Bean
//    public SmsCodeAuthenticationFilter smsCodeAuthenticationFilter() throws Exception {
//        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
//        try {
//            smsCodeAuthenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(smsAuthenticationSuccessHandler);
//        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(userAuthenticationFailureHandler);
//        smsCodeAuthenticationFilter.setRedisTemplate(redisTemplate);
//        return smsCodeAuthenticationFilter;
//    }
//
//    @Bean
//    public WeChatAuthenticationFilter WeChatAuthenticationFilter() throws Exception {
//        WeChatAuthenticationFilter weChatAuthenticationFilter = new WeChatAuthenticationFilter();
//        try {
//            weChatAuthenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        weChatAuthenticationFilter.setAuthenticationSuccessHandler(weChatAuthenticationSuccessHandler);
//        weChatAuthenticationFilter.setAuthenticationFailureHandler(userAuthenticationFailureHandler);
//        return weChatAuthenticationFilter;
//    }
}
