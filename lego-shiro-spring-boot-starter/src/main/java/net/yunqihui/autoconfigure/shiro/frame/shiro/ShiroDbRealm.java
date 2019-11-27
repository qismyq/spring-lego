//package net.yunqihui.autoconfigure.shiro.frame.shiro;
//
//import net.yunqihui.autoconfigure.shiro.JWTToken;
//import net.yunqihui.autoconfigure.shiro.JWTUtils;
//import net.yunqihui.starter.user.entity.User;
//import net.yunqihui.starter.user.service.IUserRoleService;
//import net.yunqihui.starter.user.service.IUserService;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
///**
// * @Description shiro数据库认证及权限查询
// * @Author Michael Wong
// * @Email michael_wong@yunqihui.net
// * @Date 2019/5/24 10:23
// **/
//@Component("shiroDbRealm")
//public class ShiroDbRealm extends AuthorizingRealm {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    @Lazy
//    private IUserService userService;
//    @Autowired
//    @Lazy
//    private IUserRoleService userRoleService;
//
//
//    public ShiroDbRealm() {
//        // 认证
//        // super.setAuthenticationCacheName(GlobalStatic.authenticationCacheName);
//        super.setAuthenticationCachingEnabled(false);
//
//        // 授权
//        super.setAuthorizationCachingEnabled(false);
//        // 授权不在缓存,统一有spring cache提供授权结果
//        //super.setAuthorizationCacheName(GlobalStatic.authorizationCacheName);
//        //todo
////        super.setName(GlobalStatic.authorizingRealmName);
//
//        //设置密码匹配方式
//        //super.setCredentialsMatcher(frameHashedCredentialsMatcher);
//    }
//
//    /**
//     * 必须重写此方法，不然Shiro会报错
//     */
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof JWTToken;
//    }
//
//    /**
//     * @desc: 授权
//     * @param principalCollection
//     * @return: org.apache.shiro.authz.AuthorizationInfo
//     * @auther: Michael Wong
//     * @email:  michael_wong@yunqihui.net
//     * @date:   2019/9/17 18:14
//     * @update:
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//
//        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
//        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
//        if (!SecurityUtils.getSubject().isAuthenticated()) {
//            doClearCache(principalCollection);
//            SecurityUtils.getSubject().logout();
//            return null;
//        }
//
//        ShiroUser shiroUser = (ShiroUser) principalCollection
//                .getPrimaryPrincipal();
//        // String userId = (String)
//        // principalCollection.fromRealm(getName()).iterator().next();
//        Long userId = shiroUser.getId();
//        if (userId == null) {
//            return null;
//        }
//        // 添加角色及权限信息
//        SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
//        try {
//            sazi.addRoles(userRoleService.getRoleCodeAsString(userId));
//            // TODO 需要补充权限信息
////            sazi.addStringPermissions(userRoleMenuService
////                    .getPermissionsAsString(userId));
//        } catch (Exception e) {
//            logger.error(e.getMessage(),e);
//        }
//
//        return sazi;
//    }
//
//    /**
//     * @desc: 认证
//     * @param token
//     * @return: org.apache.shiro.authc.AuthenticationInfo
//     * @auther: Michael Wong
//     * @email:  michael_wong@yunqihui.net
//     * @date:   2019/9/17 18:14
//     * @update:
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
////        JWTToken upToken = (JWTToken) token;
//
//        // 调用业务方法
//        User user = null;
//
//        String JWTToken = (String)token.getCredentials() ;
//
//        String username = JWTUtils.getUsername(JWTToken);
//        if (StringUtils.isBlank(username)) {
//            throw new AuthenticationException("token invalid");
//        }
//
//        try {
//            user = userService.getLoginUser(username,"是");
//        } catch (Exception e) {
//            logger.error(e.getMessage(),e);
//            throw  new AuthenticationException(e);
//        }
//
//        if (user != null) {
//
//            if (! JWTUtils.verify(JWTToken, username, "123456")) {
//                throw new AuthenticationException("Username or password error");
//            }
//
//
//            // 要放在作用域中的东西，请在这里进行操作
//            // SecurityUtils.getSubject().getSession().setAttribute("c_user",
//            // user);
//            // byte[] salt = EncodeUtils.decodeHex(user.getSalt());
//
//            //Session session = SecurityUtils.getSubject().getSession(false);
//            AuthenticationInfo authinfo = new SimpleAuthenticationInfo(token, token, getName());
////            AuthenticationInfo authinfo = new SimpleAuthenticationInfo(username, JWTToken, getName());
//            // Cache<Object, Object> cache =
//            // cacheManager.getCache(GlobalStatic.authenticationCacheName);
//            // cache.put(GlobalStatic.authenticationCacheName+"-"+userName,
//            // session.getId());
//
//            return authinfo;
//        }
//        // 认证没有通过
//        return null;
//    }
//
//
//}
