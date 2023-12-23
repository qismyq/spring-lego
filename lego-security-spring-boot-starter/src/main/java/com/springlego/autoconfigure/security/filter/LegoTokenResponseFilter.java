package com.springlego.autoconfigure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class LegoTokenResponseFilter extends OncePerRequestFilter {

    private TokenStore tokenStore;

    public LegoTokenResponseFilter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/oauth/token")) {
            // 使用一个包装响应来捕获原始的响应内容
            CapturingHttpServletResponse capturingResponse = new CapturingHttpServletResponse(response);

            filterChain.doFilter(request, capturingResponse);

            if (capturingResponse.getStatus() == HttpStatus.OK.value()) {
                // 获取原始的响应内容
                byte[] originalContent = capturingResponse.getByteArray();

                // 解析原始的响应内容以获取OAuth2AccessToken
                OAuth2AccessToken accessToken = parseAccessToken(originalContent);
                if (accessToken != null) {
                    // 创建自定义响应数据结构
                    ReturnDatas customResponse = ReturnDatas.getSuccessReturnDatas().setData(accessToken);
                    // 将自定义响应内容写回响应体
                    writeLegoContent(response, customResponse);
                }else {
                    response.setContentLength(capturingResponse.getContentLength());
                    StreamUtils.copy(capturingResponse.getByteArrayAsInputStream(), response.getOutputStream());
                }
            } else {
                // 如果状态码不是200 OK，直接将原始响应内容写回响应体
                response.setContentLength(capturingResponse.getContentLength());
                StreamUtils.copy(capturingResponse.getByteArrayAsInputStream(), response.getOutputStream());
            }
        } else {
            // 对于其他请求，直接传递给下一个过滤器
            filterChain.doFilter(request, response);
        }
    }

    private OAuth2AccessToken parseAccessToken(byte[] originalContent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> map = objectMapper.readValue(new String(originalContent, StandardCharsets.UTF_8), Map.class);
//
//        String accessTokenValue = (String) map.get("value");
//        OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessTokenValue);

        DefaultOAuth2AccessToken accessToken = objectMapper.readValue(new String(originalContent, StandardCharsets.UTF_8), DefaultOAuth2AccessToken.class);

        return accessToken;
    }

    private void writeLegoContent(HttpServletResponse response, ReturnDatas customResponse) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReturnDatas> entity = new HttpEntity<>(customResponse, headers);

        ObjectMapper objectMapper = new ObjectMapper();
        String customJsonResponse = objectMapper.writeValueAsString(customResponse);

        ServletServerHttpResponse servletServerHttpResponse = new ServletServerHttpResponse(response);
        servletServerHttpResponse.getBody().write(customJsonResponse.getBytes(StandardCharsets.UTF_8));
    }

    // 自定义的HttpServletResponse包装类，用于捕获原始响应内容
    private static class CapturingHttpServletResponse extends HttpServletResponseWrapper {

        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private PrintWriter writer;

        public CapturingHttpServletResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                }

                @Override
                public void write(int b) throws IOException {
                    outputStream.write(b);
                }
            };
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            if (writer == null) {
                writer = new PrintWriter(new OutputStreamWriter(outputStream, getCharacterEncoding()));
            }
            return writer;
        }

        public byte[] getByteArray() {
            return outputStream.toByteArray();
        }

        public int getContentLength() {
            return outputStream.size();
        }

        public InputStream getByteArrayAsInputStream() {
            return new ByteArrayInputStream(getByteArray());
        }
    }
}
