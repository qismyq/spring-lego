package com.springlego.autoconfigure.security.token;

import cn.hutool.core.bean.BeanUtil;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

/**
 *
 * @Description 令牌添加额外信息，做信息增强
 * @date 2022/10/6 下午 05:36
 * @author michael_wang
 */
public class OAuthTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if(accessToken instanceof DefaultOAuth2AccessToken){
            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;

            // 这里是做嵌入token内部的额外信息的，比如用户信息等等
//            ReturnDatas successReturnDatas = ReturnDatas.getSuccessReturnDatas();
//            Map<String, Object> info = BeanUtil.beanToMap(successReturnDatas);
//            token.setAdditionalInformation(info);

            return token;
        }

        return accessToken;
    }


}
