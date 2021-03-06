package net.yunqihui.autoconfigure.user.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
import net.yunqihui.autoconfigure.shiro.errorhandler.ShiroErrorCodeEnum;
import net.yunqihui.autoconfigure.shiro.token.PasswordToken;
import net.yunqihui.autoconfigure.shiro.util.IpUtil;
import net.yunqihui.autoconfigure.shiro.util.RequestResponseUtil;
import net.yunqihui.autoconfigure.user.entity.User;
import net.yunqihui.autoconfigure.user.service.ILoginService;
import net.yunqihui.autoconfigure.user.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 登录
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/25 15:24
 **/
@Api(description = "登录")
@Slf4j
@RestController
public class LoginController {


    @Autowired
    private IUserService userService;
    @Autowired
    private ILoginService loginService;


    @ApiOperation(value = "管理用户登录", notes = "POST用户登录签发JWT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "account", value = "账户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query",name = "timestamp", value = "时间戳", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="header", name = "source", value = "请求来源，固定为SYSTEM", required = true, dataType = "String",defaultValue = "SYSTEM")
    })
    @ApiResponses({
            @ApiResponse(code = 40200,message="无效请求"),
            @ApiResponse(code = 40201,message="用户密码认证失败"),
            @ApiResponse(code = 40203,message="无访问权限"),
            @ApiResponse(code = 40204,message="认证失效，请重新登录"),
            @ApiResponse(code = 40205,message="认证错误")
    })
    @RequestMapping(value = "/system/login",method = RequestMethod.POST)
    public ReturnDatas accountLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)throws Exception{
        String account = user.getAccount();
        User authUser = userService.getLoginUser(account,"是");
        Subject subject = SecurityUtils.getSubject();
        String source = RequestResponseUtil.getRequestHeaders(request).get("source");
        String host = IpUtil.getIpFromRequest(WebUtils.toHttp(request));
        PasswordToken passwordToken = new PasswordToken(account, user.getPassword(), "123644", host, source);
        try {
            subject.login(passwordToken);
        }catch (AuthenticationException e) {
            log.warn(passwordToken.getPrincipal()+"::"+e.getMessage());
            // 返回response告诉客户端认证失败
            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40201);
            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
        }catch (Exception e) {
            log.error(passwordToken.getPrincipal()+"::认证异常::"+e.getMessage(),e);
            // 返回response告诉客户端认证失败
            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(FrameErrorCodeEnum.E_50000);
            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
        }
        if (authUser != null) {
            String jwt = loginService.issueSystemJWT(account);
            authUser.setPassword(null);
            authUser.setToken(jwt);
        }

        return ReturnDatas.getSuccessReturnDatas().setData(authUser);
    }



    /**
     * @desc: 前端用户登录
     * @param account 账号
     * @return: net.yunqihui.autoconfigure.frame.entity.ReturnDatas
     *          只返回token以及账户信息，不再返回账户详情，详情信息获取由个人信息接口维护
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/4 0004 16:53       
     * @update:        
     */
    @ApiOperation(value = "前端用户登录", notes = "POST用户登录签发JWT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "account", value = "账户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="form", name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="form", name = "timestamp", value = "时间戳", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40200,message="无效请求"),
            @ApiResponse(code = 40201,message="用户密码认证失败"),
            @ApiResponse(code = 40203,message="无访问权限"),
            @ApiResponse(code = 40204,message="认证失效，请重新登录"),
            @ApiResponse(code = 40205,message="认证错误")
    })
    @PostMapping("/login")
    public ReturnDatas frontAccountLogin(String account) throws Exception {

        ReturnDatas successReturnDatas = ReturnDatas.getSuccessReturnDatas();

//        String jwt = loginService.issueFrontJWT(account);
//
//        FrontUser frontUser = new FrontUser();
//        frontUser = frontUser.selectOne(new QueryWrapper<FrontUser>().eq("account", account));
//        if (frontUser == null) {
//            return ReturnDatas.getErrorReturnDatas(UserErrorCodeEnum.E_40100);
//        }
//        frontUser.setToken(jwt);
//        successReturnDatas.setData(frontUser);

        return successReturnDatas;
    }
}
