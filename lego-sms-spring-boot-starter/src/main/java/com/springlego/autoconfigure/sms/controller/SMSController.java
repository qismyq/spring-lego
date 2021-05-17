package com.springlego.autoconfigure.sms.controller;

import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import com.springlego.autoconfigure.sms.service.ISmsContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-lego
 * @description: 短信服务
 * @author: Michael Wong
 * @create: 2021-04-27 21:24
 **/
@RestController
@RequestMapping("/sms")
public class SMSController {

    @Autowired
    private ISmsContentService smsContentService;

    @GetMapping("/send")
    public ReturnDatas sendSMS()throws Exception{
        smsContentService.send("13007549295",2);
        return ReturnDatas.getSuccessReturnDatas();
    }
}
