package com.springlego.autoconfigure.security.config;

import com.springlego.autoconfigure.security.filter.ValidateCodeFilter;
import com.springlego.autoconfigure.security.handler.UserAuthenticationFailureHandler;
import com.springlego.autoconfigure.security.handler.UserAuthenticationSuccessHandler;
import com.springlego.autoconfigure.security.provider.LegoAuthenticationProvider;
import com.springlego.autoconfigure.security.user.service.LegoUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Classname WebSecurityConfig
 * @Description Spring Security配置
 * @Date 2022/10/06 下午 06:08
 * @Created by michael wong
 */
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter /*implements ApplicationContextAware */{
    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationFailureHandler userAuthenticationFailureHandler;
    @Autowired
    private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;

    @Autowired
    private  ApplicationContext applicationContext;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(legoAuthenticationProvider());
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
        // 增加验证码验证filter
        Map<String, ValidateCodeFilter> ValidateCodeFilterType = applicationContext.getBeansOfType(ValidateCodeFilter.class);
        if (ValidateCodeFilterType.size() != 0) {
            ValidateCodeFilterType.forEach((key, value) -> http.addFilterBefore(value,UsernamePasswordAuthenticationFilter.class));
        }

        http
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/authorize").authenticated()
            .anyRequest().permitAll()
            .and().oauth2Login().loginPage("/oauth/token").successHandler(userAuthenticationSuccessHandler)
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/authorize").authenticated()
                .anyRequest().permitAll()
//                .and().oauth2Login().successHandler(userAuthenticationSuccessHandler)
                .and().csrf().disable().build()
        ;
    }
}
