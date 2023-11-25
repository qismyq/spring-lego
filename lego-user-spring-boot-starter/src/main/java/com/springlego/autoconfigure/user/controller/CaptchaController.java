package com.springlego.autoconfigure.user.controller;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import com.springlego.autoconfigure.frame.errorhandler.FrameCodeEnum;
import com.springlego.autoconfigure.frame.util.SpringContextHolder;
import com.springlego.autoconfigure.user.config.properties.CaptchaProperties;
import com.springlego.autoconfigure.user.enums.CaptchaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码操作处理
 * Thanks for Lion Li
 * @author Lion Li
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class CaptchaController {

    private final CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     */
    @GetMapping("/admin/base/open/captcha")
    public ReturnDatas getCode() {
        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
//        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        // 生成验证码
        CaptchaType captchaType = captchaProperties.getType();
        if (captchaType == null) {
            return ReturnDatas.getErrorReturnDatas(FrameCodeEnum.E_CONFIG_ERROR);
        }
        boolean isMath = CaptchaType.MATH == captchaType;
        Integer length = isMath ? captchaProperties.getNumberLength() : captchaProperties.getCharLength();
        CodeGenerator codeGenerator = ReflectUtil.newInstance(captchaType.getClazz(), length);
        AbstractCaptcha captcha = SpringContextHolder.getBeanByType(captchaProperties.getCategory().getClazz());
        captcha.setGenerator(codeGenerator);
        captcha.createCode();
        String code = captcha.getCode();
        if (isMath) {
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(StringUtils.remove(code, "="));
            code = exp.getValue(String.class);
        }
        // todo 集成redis
//        RedisUtils.setCacheObject(verifyKey, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
        Map<String, String> result = new HashMap<>();
        result.put("captchaId", uuid);
        result.put("data", "data:image/png;base64,"+captcha.getImageBase64());
        return ReturnDatas.getSuccessReturnDatas().setData(result);
    }

}
