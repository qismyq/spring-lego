package net.yunqihui.autoconfigure.wechat.service;

import com.alibaba.fastjson.JSONObject;
import net.yunqihui.autoconfigure.wechat.entity.PlatformsAuthInfo;

/**
 * @Description 微信相关授权信息
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/6 16:28
 **/
public interface IWeChatAuthService {

    /**
     * @desc: 获取第三方平台的验证票据
     * @param nonce 随机串
     * @param signature 签名
     * @param timestamp 时间戳
     * @param msgSignature 消息签名
     * @return: java.lang.String
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/6 16:31
     * @update:
     */
    Boolean componentVerifyTicket(String nonce,String signature,String timestamp,String msgSignature,String msgXml) throws Exception;


    /**
     * @desc: 获取第三方平台的access token
     * @return: java.lang.String
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/11 15:08
     * @update:
     */
    String getComponentAccessToken() throws Exception;

    /**
     * @desc: 获取预授权码
     * @return: java.lang.String
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/11 15:09       
     * @update:        
     */
    String getPreAuthCode() throws Exception;

    /**
     * @desc: 发起授权
     * @param
     * @return: com.alibaba.fastjson.JSONObject component_appid和pre_auth_code
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/12 14:46
     * @update:
     */
    JSONObject launchAuthorization() throws Exception;

    /**
     * @desc:  根据授权码获取授权信息
     * @param authCode 授权码（通过预授权码拿到的授权码）
     * @return: net.yunqihui.autoconfigure.wechat.entity.PlatformsAuthInfo 授权信息
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/13 15:24
     * @update:
     */
    PlatformsAuthInfo getAuthInfoByAuthCode(String authCode) throws Exception;
}
