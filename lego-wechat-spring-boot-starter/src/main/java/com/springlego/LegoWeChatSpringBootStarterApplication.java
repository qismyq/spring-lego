package com.springlego;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.springlego.**.mapper")
@EnableCaching
public class LegoWeChatSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegoWeChatSpringBootStarterApplication.class, args);
    }

}
