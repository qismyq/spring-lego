package net.yunqihui.autoconfigure.sms.util;

import cn.hutool.json.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@ConfigurationProperties(prefix = "sms")
public class SMSUtil {


    private static StringRedisTemplate staticRedisTemplate ;
    private static String staticAliAccessKeyId;
    private static String staticAliAccessKeySecret;
    private static String staticAliSignName;

    private String aliAccessKeyId;
    private String aliAccessKeySecret;
    private String aliSignName;

    private static String channel = "aliyun";

    @PostConstruct
    public void  init(){
        log.info("sms property init:{},{},{}",this.aliSignName,this.aliAccessKeyId,this.aliAccessKeySecret);
        staticAliAccessKeyId = this.aliAccessKeyId;
        staticAliAccessKeySecret = this.aliAccessKeySecret;
        staticAliSignName = this.aliSignName;
    }



    //发送短信
    public static String sendSMS(String mobile, String content,String templateCode) throws Exception {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("code", content);
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",staticAliAccessKeyId , staticAliAccessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi",  "dysmsapi.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        try {
            request.setSignName(staticAliSignName);
            request.setTemplateCode(templateCode);
            request.setTemplateParam(jsonObj.toString());
            request.setPhoneNumbers(mobile);
            SendSmsResponse sendSmsResponse = client.getAcsResponse(request);
            log.info("------->sms:{}", request.getSignName());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }


    public String getAliAccessKeyId() {
        return aliAccessKeyId;
    }

    public void setAliAccessKeyId(String aliAccessKeyId) {
        this.aliAccessKeyId = aliAccessKeyId;
    }

    public String getAliAccessKeySecret() {
        return aliAccessKeySecret;
    }

    public void setAliAccessKeySecret(String aliAccessKeySecret) {
        this.aliAccessKeySecret = aliAccessKeySecret;
    }

    public String getAliSignName() {
        return aliSignName;
    }

    public void setAliSignName(String aliSignName) {
        this.aliSignName = aliSignName;
    }
}
