package com.springlego.autoconfigure.security.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.security.user.entity.model.UserAccount;
import com.springlego.autoconfigure.security.user.entity.vo.UserAccountVO;

/**
 * @Classname UserAccountService
 * @Description UserAccountService
 * @Date 2023/1/16 上午 09:50
 * @author  by H2018452
 */
public interface UserAccountService extends IService<UserAccount> {

    UserAccountVO getByUsername(String username);
}
