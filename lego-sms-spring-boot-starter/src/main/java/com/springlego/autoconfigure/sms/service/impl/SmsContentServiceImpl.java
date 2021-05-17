package com.springlego.autoconfigure.sms.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.frame.errorhandler.ErrorMessageException;
import com.springlego.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
import com.springlego.autoconfigure.sms.entity.SMSChanel;
import com.springlego.autoconfigure.sms.entity.SmsContent;
import com.springlego.autoconfigure.sms.factory.SmsChannelFactory;
import com.springlego.autoconfigure.sms.mapper.SmsContentMapper;
import com.springlego.autoconfigure.sms.service.ISmsContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 短信模板 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2020-07-13
 */
@Service
public class SmsContentServiceImpl extends ServiceImpl<SmsContentMapper, SmsContent> implements ISmsContentService {


    public static final String SMS = "sms:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SmsChannelFactory smsChannelFactory;

    @Autowired
    private SmsContentMapper smsContentMapper;


    @Override
    public void send(String phone, Integer type) throws Exception {
        if (StringUtils.isBlank(phone) || type == null) {
            return;
        }

        String rediskey = SMS + phone;

        if (stringRedisTemplate.hasKey(rediskey)) {
            throw new ErrorMessageException(FrameErrorCodeEnum.E_40001);
        }

        // todo 短信模版内容读取从redis中获取
        SmsContent smsContent = smsContentMapper.selectById(type);
//        if (smsContent == null) {
//            throw new ErrorMessageException(FrameErrorCodeEnum.E_40002);
//        }

        //生成随机码
        String smscode = RandomUtil.randomNumbers(6);

//        SMSUtil.sendSMS(phone, smscode,smsContent.getTemplateCode());
        SMSChanel smsChanel = smsChannelFactory.getSMSChanel();
//        smsChanel.sendSMS(phone,smscode,smsContent.getTemplateCode());
        smsChanel.sendSMS(phone,smscode,"");

        // 往redis中保存sms
        stringRedisTemplate.opsForValue().set(rediskey,smscode,58, TimeUnit.SECONDS);

    }

    @Override
    public boolean verifySmsCode(String phone, String code) throws Exception {

        String rediskey = SMS + phone;
        if (stringRedisTemplate.hasKey(rediskey)) {
            String redisCode = stringRedisTemplate.opsForValue().get(rediskey);
            if (redisCode.equals(code)) {
                stringRedisTemplate.delete(rediskey);
                return true;
            }
        }
        return false;
    }
}
