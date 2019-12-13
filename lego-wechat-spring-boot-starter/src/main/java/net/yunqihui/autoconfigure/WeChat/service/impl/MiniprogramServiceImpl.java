package net.yunqihui.autoconfigure.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.common.util.HttpClientUtils;
import net.yunqihui.autoconfigure.frame.errorhandler.ErrorMessageException;
import net.yunqihui.autoconfigure.wechat.entity.WeChatStatic;
import net.yunqihui.autoconfigure.wechat.errorhandler.WeChatErrorCodeEnum;
import net.yunqihui.autoconfigure.wechat.service.IMiniprogramService;
import net.yunqihui.autoconfigure.wechat.service.IWeChatAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 小程序业务 服务实现类
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/13 16:05
 **/
@Slf4j
@Service
public class MiniprogramServiceImpl implements IMiniprogramService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    IWeChatAuthService weChatAuthService;

    @Override
    public boolean fastRegister(@NotBlank String name, @NotBlank String code, @NotNull Integer codeType, @NotBlank String legalPersonaWechat, @NotNull String legalPersonaName) throws Exception {


        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        JSONObject wechatCache = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);

        String componentConntactPhone = wechatCache.getString("componentConntactPhone");

        String accessToken = weChatAuthService.getComponentAccessToken();

        if (StringUtils.isBlank(accessToken)){
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50300);
        }

        String url = "https://api.weixin.qq.com/cgi-bin/component/fastregisterweapp?action=create&component_access_token=" + accessToken;

        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("code", code);
        params.put("code_type", codeType);
        params.put("legal_persona_wechat", legalPersonaWechat);
        params.put("legal_persona_name", legalPersonaName);
        params.put("component_phone", componentConntactPhone);


        String result = HttpClientUtils.sendHttpPost(url, params.toString());

        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.containsKey("errcode") && !"0".equals(resultJson.getString("errcode"))) {
            log.error("get wechat authorization_info has an error,errcode:{},errmsg:{}",
                    resultJson.getString("errcode"),
                    resultJson.getString("errmsg"));
            return false;
        }

        return true;

    }
}
