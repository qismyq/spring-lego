package net.yunqihui.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.yunqihui.starter.frame.util.FrameObjectMapper;
import org.apache.commons.io.Charsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MessageConvertConfig {
	@Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    	//设置日期格式
    	ObjectMapper objectMapper = new FrameObjectMapper();
    	mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
    	//设置中文编码格式
    	List<MediaType> list = new ArrayList<MediaType>();
    	list.add(MediaType.APPLICATION_JSON_UTF8);
    	mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
    	return mappingJackson2HttpMessageConverter;
    }
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter(){
		StringHttpMessageConverter converter=new StringHttpMessageConverter();
		converter.setDefaultCharset(Charsets.toCharset("UTF-8"));
		return converter;
	}
	@Bean
    public ResourceHttpMessageConverter resourceHttpMessageConverter() {
        ResourceHttpMessageConverter resourceHttpMessageConverter=new ResourceHttpMessageConverter();
        return resourceHttpMessageConverter;
    }
    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter=new ByteArrayHttpMessageConverter();
        return byteArrayHttpMessageConverter;
    }
    //会自动 注入的
//    @Override
//    public   void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(mappingJackson2HttpMessageConverter());
//        converters.add(stringHttpMessageConverter());
//        converters.add(resourceHttpMessageConverter());
//        converters.add(byteArrayHttpMessageConverter());
//        
//      }
}
