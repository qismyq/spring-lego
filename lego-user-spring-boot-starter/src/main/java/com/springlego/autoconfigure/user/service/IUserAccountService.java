package com.springlego.autoconfigure.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.user.dto.dataobject.UserAccountDO;

/**
 * <p>
 * 后台用户 服务类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
public interface IUserAccountService extends IService<UserAccountDO> {

    /**
     * @desc: 根据登录账号获取账号信息
     * @param account 账号
     * @param state 账号状态
     * @return: com.example.demo.model.User
     * @auther: Michael Wong
     * @email:  michael_wang90@163.com
     * @date:   2019/6/19 20:28
     * @update:
     */
    UserAccountDO getLoginUser(String account, Integer state) throws Exception;

    /**
     * 通过用户 ID 查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    UserAccountDO getUserById(String id);

}
