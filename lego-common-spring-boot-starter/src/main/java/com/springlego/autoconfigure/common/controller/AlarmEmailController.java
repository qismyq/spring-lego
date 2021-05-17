package net.yunqihui.autoconfigure.common.controller;


import io.swagger.annotations.Api;
import net.yunqihui.autoconfigure.common.entity.AlarmEmail;
import net.yunqihui.autoconfigure.common.service.IAlarmEmailService;
import net.yunqihui.autoconfigure.common.service.IMailService;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 警报邮箱 前端控制器
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Api(description = "警告邮箱")
@RestController
@RequestMapping("/alarmEmail")
public class AlarmEmailController {


    @Autowired
    IAlarmEmailService alarmEmailService;
    @Autowired
    IMailService mailService;


    @RequestMapping(method = RequestMethod.GET)
    public ReturnDatas list()throws Exception {
        ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
        List<AlarmEmail> list = alarmEmailService.list();
        returnObject.setData(list);
        return returnObject;
    }


}

