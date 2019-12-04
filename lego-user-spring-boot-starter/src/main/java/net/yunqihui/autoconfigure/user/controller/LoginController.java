package net.yunqihui.autoconfigure.user.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.user.entity.User;
import net.yunqihui.autoconfigure.user.service.ILoginService;
import net.yunqihui.autoconfigure.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/system/login")
    public ReturnDatas accountLogin(@RequestParam String account)throws Exception{

        User authUser = userService.getLoginUser(account,"是");
        if (authUser != null) {
            String jwt = loginService.issueSystemJWT(account);
            authUser.setPassword(null);
            authUser.setToken(jwt);
        }

        return ReturnDatas.getSuccessReturnDatas().setData(authUser);
    }



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

        String jwt = loginService.issueFrontJWT(account);

        // todo 前端业务

        return successReturnDatas;
    }
}
