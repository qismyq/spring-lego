package net.yunqihui.autoconfigure.shiro.controller;

import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.shiro.entity.vo.ShiroStatic;
import net.yunqihui.autoconfigure.shiro.provider.AccountProvider;
import net.yunqihui.autoconfigure.shiro.util.JsonWebTokenUtil;
import net.yunqihui.starter.user.entity.User;
import net.yunqihui.starter.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description 登录
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/25 15:24
 **/
@Slf4j
@RestController
public class LoginController {


    @Autowired
    private IUserService userService;

//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public ReturnDatas login(String userName,String password)throws Exception {
//
//        Subject user = SecurityUtils.getSubject();
//        user.logout();
//
//        ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
//
//
//        // 查询数据，看是否有此账号
//        User currUser = null;
//        try {
//            currUser = userService.getOne(new QueryWrapper<User>()
//                    .eq("account", userName)
//                    .eq("state", "是"));
//        } catch (Exception e) {
//            return ReturnDatas.getErrorReturnDatas(UserErrorCodeEnum.E_50100);
//        }
//        if (currUser == null) {
//            return ReturnDatas.getErrorReturnDatas(UserErrorCodeEnum.E_40100);
//        }
//        //通过账号和密码获取 UsernamePasswordToken token
////        JWTToken token = new JWTToken(userName,currUser.getPassword(), JWTUtils.sign(userName,currUser.getPassword()));
//        JWTToken token = new JWTToken(JWTUtils.sign(userName,password));
//        //会调用 shiroDbRealm 的认证方法 org.springrain.frame.shiro.ShiroDbRealm.doGetAuthenticationInfo(AuthenticationToken)
//        try {
//            user.login(token);
//        }catch (UnknownAccountException uae) {
//            returnDatas = ReturnDatas.getErrorReturnDatas(UserErrorCodeEnum.E_40100);
//        } catch (IncorrectCredentialsException ice) {
//            returnDatas = ReturnDatas.getErrorReturnDatas(UserErrorCodeEnum.E_40101);
//        }
//
//        return returnDatas;
//    }


    @Autowired
    AccountProvider accountProvider;
    @Autowired
    private StringRedisTemplate redisTemplate;


    /* *
    * @Description 这里已经在 passwordFilter 进行了登录认证
    * @Param [] 登录签发 JWT
    * @Return java.lang.String
    */
    @ApiOperation(value = "用户登录", notes = "POST用户登录签发JWT")
    @PostMapping("/login")
    public ReturnDatas accountLogin(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String appId = request.getParameter("appId");
        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
        String roles = accountProvider.loadAccountRole(appId);
        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        long refreshPeriodTime = 36000L;
        String jwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(), appId,
                ShiroStatic.JWT_ISSUER, refreshPeriodTime >> 2, roles, null, SignatureAlgorithm.HS512);
        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
        redisTemplate.opsForValue().set(ShiroStatic.JWT_SESSION + appId, jwt, refreshPeriodTime, TimeUnit.SECONDS);
        User authUser = userService.getLoginUser(appId,"是");
        authUser.setPassword(null);
        authUser.setToken(jwt);


        return ReturnDatas.getSuccessReturnDatas().setData(authUser);
    }

}
