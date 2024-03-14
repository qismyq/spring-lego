package com.springlego.autoconfigure.security.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LegoTokenResponseFilter extends OncePerRequestFilter {

    private TokenStore tokenStore;
    private static String SUFFIX = "/login";

    public LegoTokenResponseFilter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().endsWith(SUFFIX)) {
            // 使用一个包装响应来捕获原始的响应内容
            WrapperedResponse wrapResponse = new WrapperedResponse(response);

            filterChain.doFilter(request, wrapResponse);

            if (wrapResponse.getStatus() == HttpStatus.OK.value()) {
                // 获取原始的响应内容
                byte[] originalContent = wrapResponse.getResponseData();

                // 解析原始的响应内容以获取OAuth2AccessToken
                Map accessToken = parseAccessToken(originalContent);
                if (accessToken != null) {
                    // 创建自定义响应数据结构
                    ReturnDatas customResponse = ReturnDatas.getSuccessReturnDatas().setData(accessToken);
                    Map<String, Object> responseMap = BeanUtil.beanToMap(customResponse, true, true);
                    String customJsonResponse = JSONUtils.toJSONString(responseMap);

                    writeResponse(response, customJsonResponse);
                }else {
                    StreamUtils.copy(wrapResponse.getResponseData(), response.getOutputStream());
                }
            } else {
                // 如果状态码不是200 OK，直接将原始响应内容写回响应体
                StreamUtils.copy(wrapResponse.getResponseData(), response.getOutputStream());
            }
        } else {
            // 对于其他请求，直接传递给下一个过滤器
            filterChain.doFilter(request, response);
        }
    }


    private void writeResponse(ServletResponse response, String responseString) throws IOException {
        response.setContentLength(-1);
        PrintWriter out = response.getWriter();
        out.print(responseString);
        out.flush();
        out.close();
    }


    private Map parseAccessToken(byte[] originalContent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(new String(originalContent, StandardCharsets.UTF_8), Map.class);
        if (map.containsKey("code") && 200 != (Integer)(map.get("code"))) {
            return null;
        }
        map.put("refreshToken", ((Map) map.get("refreshToken")).get("value"));
        map.put("accessToken", map.get("value"));

        return map;
    }
}
