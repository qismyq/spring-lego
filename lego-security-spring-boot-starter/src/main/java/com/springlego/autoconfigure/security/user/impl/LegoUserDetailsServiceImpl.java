package com.springlego.autoconfigure.security.user.impl;

import com.springlego.autoconfigure.security.user.LegoUserDetailsService;
import com.springlego.autoconfigure.security.user.UserAccountService;
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
@Service
public class LegoUserDetailsServiceImpl implements LegoUserDetailsService {
    private static final String RANGE = "default";

    @Resource(name = "userAccountService_security")
    private UserAccountService userAccountService;


    @Override
    public UserDetails loadUserByUsername(String username) {
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
        UserAccountVO userAccount = userAccountService.getByUsername(username);
        UserDetail userDetail = new UserDetail();
        if (userAccount != null) {
            userDetail.setUsername(userAccount.getAccount());
            userDetail.setPassword(userAccount.getPassword());

        }
        return userDetail;
    }



    @Override
    public Boolean supports(String range) {
        return RANGE.equals(range);
    }
}
