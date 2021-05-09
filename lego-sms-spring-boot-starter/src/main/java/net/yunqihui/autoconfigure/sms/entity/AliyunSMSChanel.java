package net.yunqihui.autoconfigure.sms.entity;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.frame.errorhandler.ErrorMessageException;
import net.yunqihui.autoconfigure.sms.errorhandler.SMSErrorCodeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @program: spring-lego
 * @description: 阿里云短信通道
 * @author: Michael Wong
 * @create: 2021-03-27 22:05
 **/
@Slf4j
@ConfigurationProperties(prefix = "lego.sms")
@Service(value = "aliyun-sms")
public class AliyunSMSChanel implements SMSChanel{

    private static String regionId = "cn-hangzhou";
    private static String endpointName = "cn-hangzhou";
    private static String product = "Dysmsapi";
    private static String domain = "dysmsapi.aliyuncs.com";

    private String aliAccessKeyId;
    private String aliAccessKeySecret;
    private String aliSignName;



    @Override
    public void sendSMS(String mobile, String content,String templateCode) throws Exception {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("code", content);
        IClientProfile profile = DefaultProfile.getProfile(regionId,aliAccessKeyId , aliAccessKeySecret);
        DefaultProfile.addEndpoint(endpointName, regionId, product,  domain);
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
            throw new ErrorMessageException(SMSErrorCodeEnum.E_50100.getErrorCode(), sendSmsResponse.getMessage());
        }
    }
}
