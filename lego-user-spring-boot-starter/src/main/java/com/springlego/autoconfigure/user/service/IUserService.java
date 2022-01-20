package com.springlego.autoconfigure.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.user.entity.User;

/**
 * <p>
 * 后台用户 服务类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
public interface IUserService extends IService<User> {

    /**
     * @desc: 根据登录账号获取账号信息
     * @param account 账号
     * @param state 账号状态
     * @return: com.example.demo.model.User
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/6/19 20:28
     * @update:
     */
    User getLoginUser(String account, Integer state) throws Exception;

}
