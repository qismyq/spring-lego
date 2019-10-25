package net.yunqihui.starter.social;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("net.yunqihui.starter.social.mapper")
@EnableCaching
@EnableTransactionManagement // 开启事物支持，一般不显式声明也可以，但是建议声明一下
public class SocialSpringBootStarterAutoconfigureApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialSpringBootStarterAutoconfigureApplication.class, args);
	}

}
