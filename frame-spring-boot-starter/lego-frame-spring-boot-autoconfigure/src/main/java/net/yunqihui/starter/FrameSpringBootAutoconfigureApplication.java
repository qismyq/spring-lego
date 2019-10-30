package net.yunqihui.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FrameSpringBootAutoconfigureApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrameSpringBootAutoconfigureApplication.class, args);
	}

}
