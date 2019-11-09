package net.yunqihui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan("net.yunqihui.**.mapper")
@EnableSwagger2
@EnableCaching
public class LegoCommonSpringBootAutoconfigureApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegoCommonSpringBootAutoconfigureApplication.class, args);
	}

}
