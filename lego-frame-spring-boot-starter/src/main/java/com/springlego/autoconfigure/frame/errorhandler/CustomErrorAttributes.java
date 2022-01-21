package com.springlego.autoconfigure.frame.errorhandler;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @Description 定制4xx,5xx等错误返回信息
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/11/4 15:22
 **/
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {


    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("statusCode", errorAttributes.get("status").toString());
        errorAttributes.put("status", "error");
        errorAttributes.put("message", errorAttributes.get("error"));
        errorAttributes.remove("error");
        errorAttributes.remove("path");
        errorAttributes.remove("timestamp");
        return errorAttributes;
    }
}
