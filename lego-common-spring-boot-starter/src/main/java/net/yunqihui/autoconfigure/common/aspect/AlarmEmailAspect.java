package net.yunqihui.autoconfigure.common.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.common.entity.CommonStatic;
import net.yunqihui.autoconfigure.common.service.IMailService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description 异常捕获增强，邮件发送
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/23 10:07
 **/
@Slf4j
@Component
@Aspect
public class AlarmEmailAspect {


    @Autowired
    IMailService mailService;
    @Autowired
    CacheManager cacheManager;

    @Pointcut(value = "execution(public * net.yunqihui.autoconfigure.frame.errorhandler.ErrorMessageExceptionAdvice.defaultException(..))" +
            "&& args(request,e)",argNames = "request,e")
    public void alarmEmailCut(HttpServletRequest request,Exception e) {
    }


    @After(value = "alarmEmailCut(request,e)")
    public void defaultExceptionAfter(HttpServletRequest request,Exception e) {

        // 构造邮件内容
        StackTraceElement[] elems = e.getStackTrace();
        StringBuffer exceptionInfo = new StringBuffer("<html><body>") ;
        exceptionInfo.append("<h3>请求url：</h3>") ;
        exceptionInfo.append(request.getRequestURI()) ;
        exceptionInfo.append("<h3>入参：</h3>") ;

        // 获取入参
        Map<String,String[]> paramMap = new HashMap(request.getParameterMap()) ;
        Iterator iter = paramMap.entrySet().iterator();
        Map.Entry entry ;
        StringBuffer paramSB = new StringBuffer() ;
        while (iter.hasNext()){
            entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            paramSB.append("<div style='text-indent:15px;'><span style='color:red ;'>"+key.toString() + "</span>:" + ((String[])val)[0] + "</div><br>") ;
        }

        exceptionInfo.append(paramSB.toString()) ;
        exceptionInfo.append("<h3>" + e.toString() + "</h3>") ;
        for (StackTraceElement element:
                elems) {
            exceptionInfo.append("<div style='text-indent:15px;'>" +
                    element.getClassName() +"."+
                    element.getMethodName() +
                    "(<span style='color:red ;'>" +
                    element.getFileName() +":"+element.getLineNumber() + "</span>)" +
                    "</div><br>") ;
        }
        exceptionInfo.append("</body></html>") ;

        // 获取邮件接收人以及cc
        Cache reciverCache = cacheManager.getCache(CommonStatic.ALARM_EMAIL) ;
        String tos = reciverCache.get(CommonStatic.ALARM_EMAIL_TO,String.class) ;
        String ccs = reciverCache.get(CommonStatic.ALARM_EMAIL_CC,String.class) ;

        if (StringUtils.isBlank(tos)) {
            return;
        }
        String[] toArray = tos.split(";");


        if (StringUtils.isBlank(ccs)) {
            return;
        }
        String[] ccArray = ccs.split(";");

        String subject = "项目警告邮件";

        // 获取邮件主题
        Cache commonCache = cacheManager.getCache(CommonStatic.COMMON_CACHE_SPACE) ;
        JSONObject configJson = commonCache.get(CommonStatic.CONFIG_CACHE, JSONObject.class);
        if (configJson != null && configJson.containsKey("alarmEmailSubject")) {
            subject = configJson.getString("alarmEmailSubject");
        }

        try {
            mailService.sendHtmlMailAndCC(toArray, ccArray, subject, exceptionInfo.toString());
        } catch (Exception arounde) {
            log.error("defaultExceptionAop mail send exception:{}",arounde);
        }
    }

}

