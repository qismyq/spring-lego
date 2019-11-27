//package net.yunqihui.autoconfigure.shiro.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import lombok.extern.slf4j.Slf4j;
//import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
//import net.yunqihui.autoconfigure.shiro.JWTToken;
//import net.yunqihui.autoconfigure.shiro.JWTUtils;
//import net.yunqihui.starter.user.entity.User;
//import net.yunqihui.starter.user.errorhandler.UserErrorCodeEnum;
//import net.yunqihui.starter.user.service.IUserService;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Description 登录
// * @Author Michael Wong
// * @Email michael_wong@yunqihui.net
// * @Date 2019/11/25 15:24
// **/
//@Slf4j
//@RestController
//public class LoginController {
//
//
//    @Autowired
//    private IUserService userService;
//
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
//}
