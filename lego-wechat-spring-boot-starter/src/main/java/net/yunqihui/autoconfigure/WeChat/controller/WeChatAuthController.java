package net.yunqihui.autoconfigure.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.shiro.util.RequestResponseUtil;
import net.yunqihui.autoconfigure.wechat.service.IWeChatAuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("微信开放平台授权相关信息")
@Slf4j
@RestController
//@RequestMapping(value = "/wechatAuth")
@RequestMapping(value = "/system")
public class WeChatAuthController {


    @Autowired
    private IWeChatAuthService weChatAuthService;

    /**
     * @desc: 获取第三方平台验证票据
     * @param request
     * @param response
     * @return: net.yunqihui.autoconfigure.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/10 18:44
     * @update:
     */
//    @RequestMapping(value = "/verifyTicket", method = RequestMethod.POST)
    @RequestMapping(value = "/wx/accessCheck/json", method = RequestMethod.POST)
    public ReturnDatas componentVerifyTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String nonce = RequestResponseUtil.getParameter(request, "nonce");
        String timestamp = RequestResponseUtil.getParameter(request, "timestamp");
        String signature = RequestResponseUtil.getParameter(request, "signature");
        String msgSignature = RequestResponseUtil.getParameter(request, "msg_signature");

        StringBuilder msgSB = new StringBuilder();
        BufferedReader in = request.getReader();
        String line;
        while ((line = in.readLine()) != null) {
            msgSB.append(line);
        }
        String xml = msgSB.toString();

        Boolean ticket = weChatAuthService.componentVerifyTicket(nonce, signature, timestamp, msgSignature,xml);

        if (ticket) {
            RequestResponseUtil.responseWrite("success",response);
        }

        return null;
    }



    /**
     * @desc: 获取component_access_token
     * @return: net.yunqihui.autoconfigure.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/11 11:35
     * @update:
     */
    @RequestMapping(value = "/componetToken",method = RequestMethod.GET)
    public ReturnDatas getComponetToken()throws Exception {

        String componentAccessToken = weChatAuthService.getComponentAccessToken();

        return ReturnDatas.getSuccessReturnDatas().setData(componentAccessToken);
    }


    /**
     * @desc: 获取预授权码
     * @return: net.yunqihui.autoconfigure.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/11 15:18
     * @update:
     */
    @ApiOperation(value = "获取预授权码", notes = "获取预授权码")
    @ApiResponses({
            @ApiResponse(code = 50300,message="微信配置缺失"),
            @ApiResponse(code = 50302,message="获取预授权码失败")
    })
    @RequestMapping(value = "/preAuthCode",method = RequestMethod.GET)
    public ReturnDatas getPreAuthCode()throws Exception {

        String preAuthCode = weChatAuthService.getPreAuthCode();

        return ReturnDatas.getSuccessReturnDatas().setData(preAuthCode);
    }


    /**
     * @desc: 发起授权
     * @param
     * @return: net.yunqihui.autoconfigure.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/12 14:43
     * @update:
     */
    @ApiOperation(value = "发起授权", notes = "获取通过链接方式发起授权所需参数")
    @ApiResponses({
            @ApiResponse(code = 50300,message="微信配置缺失")
    })
    @RequestMapping(value = "/launch",method = RequestMethod.GET)
    public ReturnDatas launchAuthorization()throws Exception {

        JSONObject launchParams = weChatAuthService.launchAuthorization();

        return ReturnDatas.getSuccessReturnDatas().setData(launchParams);
    }
}
