package net.yunqihui.autoconfigure.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.common.util.HttpClientUtils;
import net.yunqihui.autoconfigure.frame.errorhandler.ErrorMessageException;
import net.yunqihui.autoconfigure.wechat.entity.WeChatStatic;
import net.yunqihui.autoconfigure.wechat.errorhandler.WeChatErrorCodeEnum;
import net.yunqihui.autoconfigure.wechat.service.IWeChatAuthService;
import net.yunqihui.autoconfigure.wechat.util.WXBizMsgCrypt;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description 微信相关授权信息 实现类
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/6 16:29
 **/
@Slf4j
@Service
public class WeChatAuthServiceImpl implements IWeChatAuthService {


    @Autowired
    CacheManager cacheManager;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean componentVerifyTicket(String nonce, String signature, String timestamp, String msgSignature, String msgXml) throws Exception {

        if (StringUtils.isBlank(nonce)
                || StringUtils.isBlank(timestamp)
                || StringUtils.isBlank(timestamp)
                || StringUtils.isBlank(msgSignature)
                || StringUtils.isBlank(msgXml)) {
            return false;
        }

        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        JSONObject wechatConfig = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);

        // 第三方平台组件加密密钥
        String encodingAesKey = wechatConfig.getString("componentEncodingAesKey");
        String componentToken = wechatConfig.getString("componentToken");
        // appId
        Document oldDocument = DocumentHelper.parseText(msgXml);
        Element oldRootElement = oldDocument.getRootElement();
        String appId = oldRootElement.elementText("AppId");

        WXBizMsgCrypt pc = new WXBizMsgCrypt(componentToken, encodingAesKey, appId);
        msgXml = pc.decryptMsg(msgSignature, timestamp, nonce, msgXml);

        Document document = DocumentHelper.parseText(msgXml);
        Element rootElement = document.getRootElement();
        String tick = rootElement.elementText("ComponentVerifyTicket");

        // 不再存入数据库，而是放入redis中
        if (StringUtils.isNotBlank(tick)) {
            stringRedisTemplate.opsForValue().set(WeChatStatic.COMPONENT_TICKET, tick);
        }

        return true;
    }


    @Override
    public String getComponentAccessToken() throws Exception {
        String redisToken = stringRedisTemplate.opsForValue().get(WeChatStatic.COMPONENT_TOKEN);
        if (StringUtils.isNotBlank(redisToken)) {
            return redisToken;
        }
        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        JSONObject wechatConfig = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);

        String appId = wechatConfig.getString("componentAppId");
        String appSecret = wechatConfig.getString("componentAppSecret");
        String ticket = stringRedisTemplate.opsForValue().get(WeChatStatic.COMPONENT_TICKET);


        if (StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret) || StringUtils.isBlank(ticket)) {
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50300);
        }

        String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token" ;

        JSONObject params = new JSONObject();
        params.put("component_appid", appId);
        params.put("component_appsecret", appSecret);
        params.put("component_verify_ticket", ticket);

        String result = HttpClientUtils.sendHttpPost(url, params.toString());

        if (StringUtils.isBlank(result)) {
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50301);
        }

        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.containsKey("component_access_token")) {
            String token = resultJson.getString("component_access_token");
            Integer expires = resultJson.getInteger("expires_in");
            // 存储redis中token并设置过期时间为官方过期时间的十分钟前。
            stringRedisTemplate.opsForValue().set(WeChatStatic.COMPONENT_TOKEN,token,expires-600, TimeUnit.SECONDS);

            return token;
        }else {
            log.error("get wechat component_access_token has an error,errcode:{},errmsg:{}",
                    resultJson.getString("errcode"),
                    resultJson.getString("errmsg"));
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50301);
        }

    }


    @Override
    public String getPreAuthCode() throws Exception {

        String redisAuthCode = stringRedisTemplate.opsForValue().get(WeChatStatic.PRE_AUTH_CODE);
        if (StringUtils.isNotBlank(redisAuthCode)) {
            return redisAuthCode;
        }

        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        JSONObject wechatConfig = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);

        String appId = wechatConfig.getString("componentAppId");
        String accessToken = this.getComponentAccessToken();

        if (StringUtils.isBlank(appId) || StringUtils.isBlank(accessToken)) {
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50300);
        }

        String url = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token="
                +accessToken ;

        JSONObject params = new JSONObject();
        params.put("component_appid", appId);

        String result = HttpClientUtils.sendHttpPost(url, params.toString());

        if (StringUtils.isBlank(result)) {
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50302);
        }

        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.containsKey("pre_auth_code")) {
            String authCode = resultJson.getString("pre_auth_code");
            Integer expires = resultJson.getInteger("expires_in");
            // 存储redis中token并设置过期时间为官方过期时间的十分钟前。
            stringRedisTemplate.opsForValue().set(WeChatStatic.PRE_AUTH_CODE,authCode,expires, TimeUnit.SECONDS);

            return authCode;
        }else {
            log.error("get wechat pre_auth_code has an error,errcode:{},errmsg:{}",
                    resultJson.getString("errcode"),
                    resultJson.getString("errmsg"));
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50302);
        }

    }


    @Override
    public String getAuthInfoByAuthCode(String authCode) throws Exception {
        return null;
    }
}
