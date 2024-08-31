package com.springlego.autoconfigure.security.util;

import com.springlego.autoconfigure.security.user.UserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

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
}
