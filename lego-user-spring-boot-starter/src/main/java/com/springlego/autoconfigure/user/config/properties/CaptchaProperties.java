package com.springlego.autoconfigure.user.config.properties;

import com.springlego.autoconfigure.user.enums.CaptchaCategory;
import com.springlego.autoconfigure.user.enums.CaptchaType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证码 配置属性
 * Thanks for Lion Li
 * @author Lion Li
 */
@Data
@Component
@ConfigurationProperties(prefix = "lego.captcha")
public class CaptchaProperties {

    /**
     * 验证码类型
     */
    private CaptchaType type;

    /**
     * 验证码类别
     */
    private CaptchaCategory category;

    /**
     * 数字验证码位数
     */
    private Integer numberLength;

    /**
     * 字符验证码长度
     */
    private Integer charLength;
}
