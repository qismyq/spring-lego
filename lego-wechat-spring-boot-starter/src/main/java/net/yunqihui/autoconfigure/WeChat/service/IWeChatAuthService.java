package net.yunqihui.autoconfigure.wechat.service;

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
    String componentVerifyTicket(String nonce,String signature,String timestamp,String msgSignature,String msgXml) throws Exception;
}
