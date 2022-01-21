package com.springlego.autoconfigure.sms.entity;

/**
 * @program: spring-lego
 * @description: 短信通道
 * @author: Michael Wong
 * @create: 2021-03-27 21:53
 **/
public interface SMSChanel {


    /** 
    * @desc:  发送短信
     * @param mobile 目标手机号
     * @param content 内容
     * @param templateCode 模板ID 
    * @return: void  
    * @auther: Michael Wong 
    * @email:  michael_wang90@163.com 
    * @date:   2021/3/27 22:10        
    * @update:         
    */
    public void sendSMS(String mobile, String content,String templateCode)throws Exception;
}
