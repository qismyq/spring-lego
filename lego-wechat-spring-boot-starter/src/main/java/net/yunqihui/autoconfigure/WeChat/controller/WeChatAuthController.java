package net.yunqihui.autoconfigure.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.shiro.util.RequestResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

/**
 * @Description 微信相关授权信息
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/6 15:06
 **/
@Slf4j
@RestController
@RequestMapping(value = "/wechatAuth")
public class WeChatAuthController {


    @RequestMapping(value = "/verifyTicket", method = RequestMethod.POST)
    public ReturnDatas componentVerifyTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String nonce = RequestResponseUtil.getParameter(request, "nonce");
        String timestamp = RequestResponseUtil.getParameter(request, "timestamp");
        String signature = RequestResponseUtil.getParameter(request, "signature");
        String msgSignature = RequestResponseUtil.getParameter(request, "msg_signature");

        log.info("nonce:{},timestamp:{},signature:{},msgSignature:{}", nonce, timestamp, signature, msgSignature);

        StringBuilder msgSB = new StringBuilder();
        BufferedReader in = request.getReader();
        String line;
        while ((line = in.readLine()) != null) {
            msgSB.append(line);
        }
        String xml = msgSB.toString();

//        boolean isValid = WXBizMsgCrypt.checkSignature(COMPONENT_TOKEN, signature, timestamp, nonce);

        return null;
    }

}
