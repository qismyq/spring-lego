package com.springlego.autoconfigure.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.sms.entity.SmsContent;

/**
 * <p>
 * 短信模板 服务类
 * </p>
 *
 * @author michael wong
 * @since 2020-07-13
 */
public interface ISmsContentService extends IService<SmsContent> {

    /**
     * @desc: 发送短信
     * @param phone
     * @return: void 
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2020/8/5 9:48       
     * @update:        
     */
    void send(String phone, Integer type)throws Exception;


    /**
     * @desc: 验证验证码
     * @param phone 手机号
     * @param code 验证码
     * @return: boolean
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2020/8/5 11:08
     * @update:
     */
    boolean verifySmsCode(String phone, String code) throws Exception;
}
