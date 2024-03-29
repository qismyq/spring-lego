package com.springlego.autoconfigure.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.user.entity.UserRole;

import java.util.Set;

/**
 * <p>
 * 用户角色中间表 服务类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
public interface IUserRoleService extends IService<UserRole> {



    Set<String> getRoleCodeAsStringByAccount(String account) throws Exception;

}
