package com.springlego.autoconfigure.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.user.entity.User;
import com.springlego.autoconfigure.user.mapper.UserMapper;
import com.springlego.autoconfigure.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

//    @Cacheable(cacheNames = {"loginUser"},key = "'account'")
    @Override
    public User getLoginUser(String account, String state) throws Exception {
        return userMapper.getLoginUser(account,state);
    }

}
