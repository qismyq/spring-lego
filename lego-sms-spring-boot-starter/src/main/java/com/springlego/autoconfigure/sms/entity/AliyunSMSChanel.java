package com.springlego.autoconfigure.sms.entity;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.springlego.autoconfigure.frame.errorhandler.ErrorMessageException;
import lombok.extern.slf4j.Slf4j;
import com.springlego.autoconfigure.sms.errorhandler.SMSErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @program: spring-lego
 * @description: 阿里云短信通道
 * @author: Michael Wong
 * @create: 2021-03-27 22:05
 **/
@Slf4j
@Service(value = "sms-aliyun")
public class AliyunSMSChanel implements SMSChanel {

    private static String regionId = "cn-hangzhou";
    private static String endpointName = "cn-hangzhou";
    private static String product = "Dysmsapi";
    private static String domain = "dysmsapi.aliyuncs.com";

    @Value("${lego.sms.accessKeyId:#{null}}")
    private String aliAccessKeyId;
    @Value("${lego.sms.accessKeySecret:#{null}}")
    private String aliAccessKeySecret;
    @Value("${lego.sms.signName:#{null}}")
    private String aliSignName;


    @Override
    public void sendSMS(String mobile, String content, String templateCode) throws Exception {
        if (StringUtils.isBlank(aliAccessKeyId) || StringUtils.isBlank(aliAccessKeySecret) || StringUtils.isBlank(aliSignName)) {
            throw new ErrorMessageException(SMSErrorCodeEnum.E_50201);
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("code", content);
        IClientProfile profile = DefaultProfile.getProfile(regionId, aliAccessKeyId, aliAccessKeySecret);
        DefaultProfile.addEndpoint(endpointName, regionId, product, domain);
        IAcsClient client = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(aliSignName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(jsonObj.toString());
        request.setPhoneNumbers(mobile);
        SendSmsResponse sendSmsResponse = client.getAcsResponse(request);
        if (!"OK".equals(sendSmsResponse.getCode())) {
            log.error(sendSmsResponse.getMessage());
            throw new ErrorMessageException(SMSErrorCodeEnum.E_50200.getErrorCode(), sendSmsResponse.getMessage());
        }
    }
}
