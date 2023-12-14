package com.springlego.autoconfigure.frame.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
	@Value("${swagger.enable}")
	private Boolean enable;
	@Value("${swagger.base.package}")
	private String basePackage;
	@Value("${swagger.path.selector.regex}")
	private String regexs;

	@Value("${swagger.api.info.title}")
	private String title;
	@Value("${swagger.api.info.description}")
	private String description;
	@Value("${swagger.api.info.version}")
	private String version;
	@Value("${swagger.api.info.url}")
	private String url;

//	@Bean
//	public Docket createRestApi() {
////		ApiSelectorBuilder selectorBuilder = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage(basePackage));
////		if(enable){
////			selectorBuilder.paths(PathSelectors.regex("/system/user/.*/json"));
////			//selectorBuilder.paths(PathSelectors.regex("/system/dicdata/.*/json"));
////		}else{
////			selectorBuilder.paths(PathSelectors.none());
////		}
////
////		Docket build = selectorBuilder.build();
////		//build.ignoredParameterTypes(DicData.class);
////		return build;
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(apiInfo())
//				.select()
//				.apis(RequestHandlerSelectors.basePackage(basePackage))
//				.paths(PathSelectors.any())
//				.build();
//	}

//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				.title(title)
//				.description(description)
//				.termsOfServiceUrl(url)
//				.version(version)
//				.build();
//	}
}
