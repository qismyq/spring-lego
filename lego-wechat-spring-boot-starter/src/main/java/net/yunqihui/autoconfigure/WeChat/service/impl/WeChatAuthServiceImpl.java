package net.yunqihui.autoconfigure.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.common.util.HttpClientUtils;
import net.yunqihui.autoconfigure.frame.errorhandler.ErrorMessageException;
import net.yunqihui.autoconfigure.wechat.entity.PlatformsAuthInfo;
import net.yunqihui.autoconfigure.wechat.entity.WeChatStatic;
import net.yunqihui.autoconfigure.wechat.errorhandler.WeChatErrorCodeEnum;
import net.yunqihui.autoconfigure.wechat.service.IMiniprogramService;
import net.yunqihui.autoconfigure.wechat.service.IPlatformsAuthInfoService;
import net.yunqihui.autoconfigure.wechat.service.IWeChatAuthService;
import net.yunqihui.autoconfigure.wechat.util.WXBizMsgCrypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    IPlatformsAuthInfoService platformsAuthInfoService;
    @Autowired
    IMiniprogramService miniprogramService;


    @Override
    public Boolean authEventCallback(String nonce, String signature, String timestamp, String msgSignature, String msgXml)throws Exception {
        if (StringUtils.isBlank(nonce)
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

        String infoType = rootElement.elementText("InfoType");

        if ("component_verify_ticket".equals(infoType)) {
            // 验证票据回调
            return this.componentVerifyTicket(rootElement);

        } else if ("notify_third_fasteregister".equals(infoType)) {
            // 快速创建小程序回调
            return miniprogramService.registerCheckCallback(rootElement);
        }

        return false;
    }

    @Override
    public Boolean componentVerifyTicket(Element element) throws Exception {

        if (element == null) {
            return false;
        }

        String tick = element.elementText("ComponentVerifyTicket");

        // 不再存入数据库，而是放入redis中
        if (StringUtils.isNotBlank(tick)) {
            stringRedisTemplate.opsForValue().set(WeChatStatic.COMPONENT_TICKET, tick);
        }

        return true;
    }


    @Override
    public String getComponentAccessToken() throws Exception {
        // 获取redis中的token，如果存在直接返回
        String redisToken = stringRedisTemplate.opsForValue().get(WeChatStatic.COMPONENT_TOKEN);
        if (StringUtils.isNotBlank(redisToken)) {
            return redisToken;
        }

        // 与微信交互获取accessToken
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
        // 获取redis中的authCode，如果存在直接返回
        String redisAuthCode = stringRedisTemplate.opsForValue().get(WeChatStatic.PRE_AUTH_CODE);
        if (StringUtils.isNotBlank(redisAuthCode)) {
            return redisAuthCode;
        }

        // 与微信交互获取authCode
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
    public JSONObject launchAuthorization() throws Exception {
        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        JSONObject wechatConfig = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);

        String componentAppId = wechatConfig.getString("componentAppId");
        String preAuthCode = stringRedisTemplate.opsForValue().get(WeChatStatic.PRE_AUTH_CODE);

        if (StringUtils.isBlank(componentAppId) || StringUtils.isBlank(preAuthCode)) {
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50300);
        }

        JSONObject params = new JSONObject();
        params.put("component_appid", componentAppId);
        params.put("pre_auth_code", preAuthCode);

        return params;
    }

    @Override
    public PlatformsAuthInfo getAuthInfoByAuthCode(String authCode) throws Exception {


        if (StringUtils.isBlank(authCode)) {
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_40300);
        }

        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        JSONObject wechatConfig = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);
        String componentAppId = wechatConfig.getString("componentAppId");

        String accessToken = this.getComponentAccessToken();

        if (StringUtils.isBlank(componentAppId) || StringUtils.isBlank(accessToken)) {
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50300);
        }

        String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token="+accessToken ;
        JSONObject params = new JSONObject();
        params.put("component_appid", componentAppId);
        params.put("authorization_code", authCode);


        String result = HttpClientUtils.sendHttpPost(url, params.toString());

        if (StringUtils.isBlank(result)) {
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50303);
        }

        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.containsKey("errcode") && !"0".equals(resultJson.getString("errcode"))) {
            log.error("get wechat authorization_info has an error,errcode:{},errmsg:{}",
                    resultJson.getString("errcode"),
                    resultJson.getString("errmsg"));
            throw new ErrorMessageException(WeChatErrorCodeEnum.E_50303);
        }else {

            String authorizerAppId = resultJson.getString("authorizer_appid");
            String authorizerAccessToken = resultJson.getString("authorizer_access_token");
            Integer expiresIn = resultJson.getInteger("expires_in");
            String authorizerRefreshToken = resultJson.getString("authorizer_refresh_token");
            JSONArray funcinfoArray = resultJson.getJSONArray("func_info");

            PlatformsAuthInfo platformsAuthInfo = new PlatformsAuthInfo();

            QueryWrapper<PlatformsAuthInfo> queryWrapper = new QueryWrapper<PlatformsAuthInfo>().eq("authorizer_appid", authorizerAppId);
            PlatformsAuthInfo queryResult = platformsAuthInfo.selectOne(queryWrapper);
            if (queryResult != null){
                throw new ErrorMessageException(WeChatErrorCodeEnum.E_40301);
            }

            platformsAuthInfo.setAuthorizerAppid(authorizerAppId)
                    .setAuthorizerRefreshToken(authorizerRefreshToken)
                    .setAuthorizerAccessToken(authorizerAccessToken);

            // 计算调用令牌失效时间
            Date expiresTime = DateUtils.addSeconds(new Date(), expiresIn);
            platformsAuthInfo.setExpiresTime(expiresTime);

            // 获取并转换权限集
            if (funcinfoArray != null) {
                JSONObject funcinfo = null;
                StringBuffer funcinfoStr = new StringBuffer();
                for (int i = 0; i < funcinfoArray.size(); i++) {
                    funcinfo = funcinfoArray.getJSONObject(i);
                    funcinfoStr.append(funcinfo.getJSONObject("funcscope_category").getInteger("id"));
                    if (i != funcinfoArray.size() - 1) {
                        funcinfoStr.append(",");
                    }
                }
                platformsAuthInfo.setFuncInfo(funcinfoStr.toString());
            }


            platformsAuthInfoService.save(platformsAuthInfo);

            return platformsAuthInfo;
        }

    }


    @Override
    public PlatformsAuthInfo getAuthInfoById(Integer id) throws Exception {

        PlatformsAuthInfo authInfo = platformsAuthInfoService.getById(id);

        if (authInfo != null) {
            // 如果接口令牌超时则刷新接口令牌
            if (new Date().after(authInfo.getExpiresTime())) {
                String componentAccessToken = getComponentAccessToken();
                String url = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=" + componentAccessToken ;

                JSONObject params = new JSONObject();
                Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
                JSONObject wechatConfigJson = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);
                params.put("component_appid", wechatConfigJson.get("componentAppId"));

                params.put("authorizer_appid", authInfo.getAuthorizerAppid());
                params.put("authorizer_refresh_token", authInfo.getAuthorizerRefreshToken());

                String result = HttpClientUtils.sendHttpPost(url, params.toString());

                if (StringUtils.isBlank(result)) {
                    throw new ErrorMessageException(WeChatErrorCodeEnum.E_50304);
                }

                JSONObject resultJson = JSONObject.parseObject(result);
                if (resultJson.containsKey("authorizer_access_token")) {
                    authInfo.setAuthorizerAccessToken(resultJson.getString("authorizer_access_token"));
                    // 计算调用令牌失效时间
                    Integer expiresIn = resultJson.getInteger("expires_in");
                    Date expiresTime = DateUtils.addSeconds(new Date(), expiresIn);
                    authInfo.setExpiresTime(expiresTime);
                    platformsAuthInfoService.update(authInfo, null);
                    return authInfo;
                }else {
                    log.error("refresh wechat authorizer_access_token has an error,errcode:{},errmsg:{}",
                            resultJson.getString("errcode"),
                            resultJson.getString("errmsg"));
                    throw new ErrorMessageException(WeChatErrorCodeEnum.E_50304);
                }

            }

        }
        return null;
    }
}
