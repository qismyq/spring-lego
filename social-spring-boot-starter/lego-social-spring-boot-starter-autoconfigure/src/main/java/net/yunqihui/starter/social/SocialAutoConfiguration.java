package net.yunqihui.starter.social;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/10/23 18:13
 **/
@Configuration
@ConditionalOnWebApplication  // web应用才生效
public class SocialAutoConfiguration {

}
