package net.yunqihui.autoconfigure.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.javaws.jnl.XMLUtils;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
import net.yunqihui.autoconfigure.wechat.entity.WeChatStatic;
import net.yunqihui.autoconfigure.wechat.service.IWeChatAuthService;
import net.yunqihui.autoconfigure.wechat.util.WXBizMsgCrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * @Description 微信相关授权信息 实现类
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/6 16:29
 **/
public class WeChatAuthServiceImpl implements IWeChatAuthService {


    @Autowired
    CacheManager cacheManager;

    @Override
    public String componentVerifyTicket(String nonce, String signature, String timestamp, String msgSignature, String msgXml) throws Exception {

        if (StringUtils.isBlank(nonce)
                || StringUtils.isBlank(timestamp)
                || StringUtils.isBlank(timestamp)
                || StringUtils.isBlank(msgSignature)
                || StringUtils.isBlank(msgXml)) {
            return "false";
        }

        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        JSONObject wechatConfig = cache.get(WeChatStatic.WECHAT_CONFIG_CACHE, JSONObject.class);

//	            LogUtil.info("第三方平台全网发布-----------------------原始 Xml="+xml);

        String encodingAesKey = wechatConfig.getString("componentEncodingAesKeyKey");// 第三方平台组件加密密钥
        String componentToken = wechatConfig.getString("componentToken");
        Document
        String appId = getAuthorizerAppidFromXml(xml);// 此时加密的xml数据中ToUserName是非加密的，解析xml获取即可
//				LogUtil.info("第三方平台全网发布-------------appid----------getAuthorizerAppidFromXml(xml)-----------appId="+appId);

        WXBizMsgCrypt pc = new WXBizMsgCrypt(componentToken, encodingAesKey, appId);
        msgXml = pc.decryptMsg(msgSignature, timestamp, nonce, msgXml);
//	            LogUtil.info("第三方平台全网发布-----------------------解密后 Xml="+xml);

//        processAuthorizationEvent(xml);


        return null;
    }
}
