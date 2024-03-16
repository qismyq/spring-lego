package com.springlego.autoconfigure.security.user.service.impl;

import com.springlego.autoconfigure.security.user.service.LegoUserDetailsService;
import com.springlego.autoconfigure.security.user.service.UserAccountService;
import com.springlego.autoconfigure.security.user.UserDetail;
import com.springlego.autoconfigure.security.user.entity.vo.UserAccountVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Classname LegoUserDetailsServiceImpl
 * @Description 多用户类型刷新token实现类
 * @Date 2022/4/11 下午 02:15
 * @author  by michael wong
 */
@Service("defaultLegoUserDetailsService")
public class LegoUserDetailsServiceImpl implements LegoUserDetailsService {
    private static final String RANGE = "default";

    @Resource(name = "userAccountService_security")
    private UserAccountService userAccountService;


    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccountVO userAccount = userAccountService.getByUsername(username);
        if (userAccount != null) {
            UserDetail userDetail = new UserDetail();
            userDetail.setUsername(userAccount.getAccount());
            userDetail.setPassword(userAccount.getPassword());
            return userDetail;
        }
        return null;
    }



    @Override
    public Boolean supports(String range) {
        return RANGE.equals(range);
    }
}
