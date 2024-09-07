package com.springlego.autoconfigure.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.user.dto.dataobject.UserAccountDO;
import com.springlego.autoconfigure.user.mapper.UserAccountMapper;
import com.springlego.autoconfigure.user.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccountDO> implements IUserAccountService {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Cacheable(cacheNames = {"loginUser"},key = "'account'")
    @Override
    public UserAccountDO getLoginUser(String account, Integer state) throws Exception {
        return userAccountMapper.getLoginUser(account,state);
    }

    @Override
    public UserAccountDO getUserById(String id) {
        return userAccountMapper.selectById(id);
    }

}
