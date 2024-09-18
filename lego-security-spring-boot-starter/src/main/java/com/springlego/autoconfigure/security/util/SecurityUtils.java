package com.springlego.autoconfigure.security.util;

import com.springlego.autoconfigure.security.user.UserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * 用户
 *
 * @author michael wong
 */
public class SecurityUtils {
    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        UserDetail user;
        try {
            user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return new UserDetail();
        }

        return user;
    }

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        return getUser().getId();
    }

    /**
     * 获取部门ID
     */
    public static String getDeptId() {
        return getUser().getDeptId();
    }


    /**
     * 设置当前用户
     *
     * @param loginUser 登录用户
     * @param authentication authenticationToken
     */
    public static void setLoginUser(UserDetails loginUser, Authentication authentication) {
        // 创建 Authentication，并设置到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 额外设置到 request 中，用于 ApiAccessLogFilter 可以获取到用户编号；
        // 原因是，Spring Security 的 Filter 在 ApiAccessLogFilter 后面，在它记录访问日志时，线上上下文已经没有用户编号等信息
//        WebFrameworkUtils.setLoginUserId(request, loginUser.getId());
//        WebFrameworkUtils.setLoginUserType(request, loginUser.getUserType());
    }

}
