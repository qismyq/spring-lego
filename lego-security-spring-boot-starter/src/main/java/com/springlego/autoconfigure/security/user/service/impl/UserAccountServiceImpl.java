package com.springlego.autoconfigure.security.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.security.user.service.UserAccountService;
import com.springlego.autoconfigure.security.user.entity.model.UserAccount;
import com.springlego.autoconfigure.security.user.entity.vo.UserAccountVO;
import com.springlego.autoconfigure.security.user.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname UserAccountServiceImpl
 * @Description default UserAccountServiceImpl
 * @Date 2023/1/16 上午 10:51
 * @author by H2018452
 */
@Service("userAccountService_security")
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public UserAccountVO getByUsername(String username) {
        String account = username;
        return userAccountMapper.getByAccount(account, 1,false);
    }
}
