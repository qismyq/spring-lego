package com.springlego.autoconfigure.common.config;

import com.alibaba.fastjson.JSONObject;
import com.springlego.autoconfigure.common.entity.AlarmEmail;
import com.springlego.autoconfigure.common.entity.CommonStatic;
import com.springlego.autoconfigure.common.service.IAlarmEmailService;
import com.springlego.autoconfigure.common.service.IConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Michael on 2017/6/27.
 * 初始化就加载config内容到缓存中
 */
@Slf4j
@Service
public class CommonBaseInitializingConfig implements InitializingBean {

    @Autowired
    IConfigurationService configurationService;
    //    @Autowired
//    ISysSysparamService sysSysparamService ;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private IAlarmEmailService alarmEmailService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("**************init common config data***************");
        // 配置表信息以及系统表信息
        JSONObject config = configurationService.findParamBean();
        Cache cache = cacheManager.getCache(CommonStatic.COMMON_CACHE_SPACE);
        cache.put(CommonStatic.CONFIG_CACHE, config);

        // 警报邮件收件人信息
        Cache alarmCache = cacheManager.getCache(CommonStatic.ALARM_EMAIL);
        List<AlarmEmail> emails = alarmEmailService.list();
        StringBuffer emailsTo = new StringBuffer();  // 收件人
        StringBuffer emailsCC = new StringBuffer();  // 抄送人
        if (emails != null && emails.size() > 0) {
            for (AlarmEmail email :
                    emails) {
                if (new Integer(1).equals(email.getType())) {  // 收件人
                    emailsTo.append(email.getEmaiAddress()).append(";");
                } else if (new Integer(2).equals(email.getType())) {  // 抄送人
                    emailsCC.append(email.getEmaiAddress()).append(";");
                }
            }
            alarmCache.put(CommonStatic.ALARM_EMAIL_TO, emailsTo.toString());
            alarmCache.put(CommonStatic.ALARM_EMAIL_CC, emailsCC.toString());
        }


    }
}
