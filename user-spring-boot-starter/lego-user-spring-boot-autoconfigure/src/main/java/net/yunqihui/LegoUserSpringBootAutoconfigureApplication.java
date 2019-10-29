package net.yunqihui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("net.yunqihui.**.mapper")
@EnableSwagger2
public class LegoUserSpringBootAutoconfigureApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegoUserSpringBootAutoconfigureApplication.class, args);
	}

}
