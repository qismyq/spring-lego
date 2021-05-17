package net.yunqihui.autoconfigure.sms.factory;

import net.yunqihui.autoconfigure.frame.util.SpringContextHolder;
import net.yunqihui.autoconfigure.sms.entity.SMSChanel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: spring-lego
 * @description: 短信通道工厂
 * @author: Michael Wong
 * @create: 2021-03-25 23:47
 **/
@Component
public class SmsChannelFactory {

    private String channel;

    @Value("${lego.sms.channel}")
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public SMSChanel getSMSChanel(){
        return (SMSChanel) SpringContextHolder.getBean(channel);
    }


}
