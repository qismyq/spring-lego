package net.yunqihui.autoconfigure.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.io.Charsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
@Order(0)
public class MessageConvertConfig implements WebMvcConfigurer{

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
		while(iterator.hasNext()){
			HttpMessageConverter<?> converter = iterator.next();
			if(converter instanceof MappingJackson2HttpMessageConverter){
				iterator.remove();
			}
		}
		FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setCharset(Charset.forName("UTF-8"));
		config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		//设置允许返回为null的属性
		config.setSerializerFeatures(SerializerFeature.PrettyFormat
//				SerializerFeature.WriteMapNullValue
//				SerializerFeature.WriteNullNumberAsZero,
//				SerializerFeature.WriteNullStringAsEmpty
		);
		fastJsonConverter.setFastJsonConfig(config);
		List<MediaType> list = new ArrayList<>();
		list.add(MediaType.APPLICATION_JSON_UTF8);
		fastJsonConverter.setSupportedMediaTypes(list);
		converters.add(fastJsonConverter);
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
