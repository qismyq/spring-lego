package com.springlego.autoconfigure.user.controller;

import com.springlego.autoconfigure.user.service.ILoginService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import com.springlego.autoconfigure.user.entity.UserAccount;
import com.springlego.autoconfigure.user.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 登录
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/11/25 15:24
 **/
@Api("登录")
@Slf4j
@RestController
public class LoginController {


    @Autowired
    private IUserAccountService userService;
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
    public ReturnDatas accountLogin(@RequestBody UserAccount user, HttpServletRequest request, HttpServletResponse response)throws Exception{
        String account = user.getAccount();
        UserAccount authUser = userService.getLoginUser(account,1);
        // todo 先只判断登录问题，鉴权等需要改造到lego-jwt模块中
//        Subject subject = SecurityUtils.getSubject();
//        String source = RequestResponseUtil.getRequestHeaders(request).get("source");
//        String host = IpUtil.getIpFromRequest(WebUtils.toHttp(request));
//        PasswordToken passwordToken = new PasswordToken(account, user.getPassword(), "123644", host, source);
//        try {
//            subject.login(passwordToken);
//        }catch (AuthenticationException e) {
//            log.warn(passwordToken.getPrincipal()+"::"+e.getMessage());
//            // 返回response告诉客户端认证失败
//            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(ShiroErrorCodeEnum.E_40201);
//            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
//        }catch (Exception e) {
//            log.error(passwordToken.getPrincipal()+"::认证异常::"+e.getMessage(),e);
//            // 返回response告诉客户端认证失败
//            ReturnDatas errorReturnDatas = ReturnDatas.getErrorReturnDatas(FrameErrorCodeEnum.E_50000);
//            RequestResponseUtil.responseWrite(JSON.toJSONString(errorReturnDatas),response);
//        }
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
     * @return: com.springlego.autoconfigure.frame.entity.ReturnDatas
     *          只返回token以及账户信息，不再返回账户详情，详情信息获取由个人信息接口维护
     * @auther: Michael Wong
     * @email:  michael_wang90@163.com
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

        // todo 前端用户登录
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
