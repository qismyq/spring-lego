package com.springlego.autoconfigure.WeChat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springlego.autoconfigure.WeChat.entity.PlatformsFastRegisterFailedHis;
import com.springlego.autoconfigure.WeChat.entity.PlatformsFastRegisterInfo;
import com.springlego.autoconfigure.WeChat.entity.WeChatStatic;
import com.springlego.autoconfigure.WeChat.service.IMiniprogramService;
import com.springlego.autoconfigure.WeChat.service.IPlatformsFastRegisterInfoService;
import com.springlego.autoconfigure.WeChat.service.IWeChatAuthService;
import lombok.extern.slf4j.Slf4j;
import com.springlego.autoconfigure.common.util.HttpClientUtils;
import com.springlego.autoconfigure.frame.errorhandler.ErrorMessageException;
import com.springlego.autoconfigure.WeChat.errorhandler.WeChatErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @Autowired
    IPlatformsFastRegisterInfoService platformsFastRegisterInfoService;

    @Override
    public boolean fastRegister(@NotBlank String openId,@NotNull Integer goodId,@NotBlank String name, @NotBlank String code, @NotNull Integer codeType, @NotBlank String legalPersonaWechat, @NotNull String legalPersonaName) throws Exception {


        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        JSONObject wechatCache = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);

        String componentConntactPhone = wechatCache.getString("componentConntactPhone");

        String accessToken = weChatAuthService.getComponentAccessToken();

        if (StringUtils.isBlank(accessToken)) {
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
            // todo 细化errcode,返回给客户端相应的合理提示语
            log.error("get wechat authorization_info has an error,errcode:{},errmsg:{}",
                    resultJson.getString("errcode"),
                    resultJson.getString("errmsg"));
            return false;
        }

        // 微信平台交互成功后将企业信息持久化到db中，方便回调时进行校验
        PlatformsFastRegisterInfo platformsFastRegisterInfo = new PlatformsFastRegisterInfo();
        platformsFastRegisterInfo.setName(name)
                .setCode(code)
                .setLegalPersonaWechat(legalPersonaWechat)
                .setLegalPersonaName(legalPersonaName)
                .setCodeType(codeType)
                .setOpenId(openId)
                .setGoodId(goodId)
                .setCreateTime(new Date());

        return true;

    }


    @Override
    public Boolean registerCheckCallback(Element element) throws Exception {
        if (element == null) {
            return false;
        }


        // 获取消息体内的企业信息，查验账号并处理结果
        Element info = element.element("info");
        String name = info.elementText("name");
        String code = info.elementText("code");
        String legalPersonaWechat = info.elementText("legal_persona_wechat");
        String legalPersonaName = info.elementText("legal_persona_name");
        String appid = element.elementText("appid");

        PlatformsFastRegisterInfo platformsFastRegisterInfo = new PlatformsFastRegisterInfo();
        platformsFastRegisterInfo.setName(name)
                .setCode(code)
                .setLegalPersonaWechat(legalPersonaWechat)
                .setLegalPersonaName(legalPersonaName) ;

        PlatformsFastRegisterInfo resultBean = platformsFastRegisterInfoService.getOne(new QueryWrapper<>(platformsFastRegisterInfo));
        if (resultBean == null){
            // db中没有相关企业信息
            return false;
        }else {

            String status = element.elementText("status");
            if ("0".equals(status)) {
                // 创建成功
                // 更新PlatformsFastRegisterInfo中的appid
                resultBean.setAppid(appid);
                platformsFastRegisterInfoService.update(resultBean,null);

                // 获取第三方授权码
                String authCode = info.elementText("auth_code");
                // 开始获取授权信息
                if (StringUtils.isNotBlank(authCode)) {
                    try {
                        weChatAuthService.getAuthInfoByAuthCode(authCode,resultBean.getId());
                        return true;
                    } catch (ErrorMessageException exception) {
                        return false;
                    }
                }else {
                    return false;
                }
            } else {
                // 创建失败，记录失败信息
                PlatformsFastRegisterFailedHis failedHis = new PlatformsFastRegisterFailedHis();
                failedHis.setCreateTime(new Date())
                        .setState(0)
                        .setFastRegisterId(resultBean.getId())
                        .setErrmsg(element.elementText("errmsg"))
                        .setErrorCode(element.elementText("error_code"));

                failedHis.insert();
            }
        }
        return true;
    }


    @Override
    public Boolean modifyDomain(@NotNull String action,@NotNull String[] requestdomain,@NotNull String[] wsrequestdomain,@NotNull String[] uploaddomain,@NotNull String[] downloaddomain) throws Exception {
        weChatAuthService.getAuthInfoById(1);
        return null;
    }
}
