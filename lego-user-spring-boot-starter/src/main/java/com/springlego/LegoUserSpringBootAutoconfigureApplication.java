package com.springlego;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@MapperScan("com.springlego.**.mapper")
@EnableCaching
@SpringBootApplication
public class LegoUserSpringBootAutoconfigureApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegoUserSpringBootAutoconfigureApplication.class, args);
	}

}


