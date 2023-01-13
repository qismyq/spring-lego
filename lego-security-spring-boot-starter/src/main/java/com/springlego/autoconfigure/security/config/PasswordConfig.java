package com.springlego.autoconfigure.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 加密方式
 *
 * @author Michael Wong
 * @mail michael_wang90@163.com
 */
@Configuration
@AllArgsConstructor
public class PasswordConfig {

    // todo 是否需要注册此bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
