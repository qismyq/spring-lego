package com.springlego.autoconfigure.security.user.impl;

import com.springlego.autoconfigure.security.user.LegoUserDetailsService;
import com.springlego.autoconfigure.security.user.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Classname LegoUserDetailsServiceImpl
 * @Description 多用户类型刷新token实现类
 * @Date 2022/4/11 下午 02:15
 * @author  by michael wong
 */
@Service
//@ConditionalOnMissingBean(LegoUserDetailsService.class)
public class LegoUserDetailsServiceImpl implements LegoUserDetailsService {
    private static final String FRONT_ROLE = "ROLE_FRONT";


    @Override
    public UserDetails loadUserByUsername(String username, String userType) {
//        // 前台用户
//        if (StringUtils.equals(StringUtils.trim(userType).toUpperCase(), UserTypeConstant.FRONT)) {
//            Result<UserDetail> result = apiAccountFeignClient.getByUsername(username);
//            UserDetail userDetail = result.getData();
//            if(userDetail == null){
//                throw new Exception(ErrorCode.ACCOUNT_NOT_EXIST);
//            }
//            userDetail.setAuthorities(AuthorityUtils.createAuthorityList(FRONT_ROLE).stream().collect(Collectors.toSet()));
//            return userDetail;
//        }
//        // 后台用户
//        else{
//            return loadUserByUsername(username);
//        }

        return null;
    }

    @Override
    public UserDetail loadUserByOpenId(Map<String,String> params) {
//        Result<UserDetail> result = apiAccountFeignClient.getByOpenId(params);
//        UserDetail userDetail = result.getData();
        UserDetail userDetail = null;
//        if(userDetail == null){
//            throw new Exception(ErrorCode.ACCOUNT_NOT_EXIST);
//        }
//        userDetail.setAuthorities(AuthorityUtils.createAuthorityList(FRONT_ROLE).stream().collect(Collectors.toSet()));
        return userDetail;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return loadUserByUsername(s);
        return null;
    }
}
