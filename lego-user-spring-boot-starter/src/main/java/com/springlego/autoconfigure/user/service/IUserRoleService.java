package com.springlego.autoconfigure.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.user.dto.dataobject.UserRoleDO;

import java.util.Set;

/**
 * <p>
 * 用户角色中间表 服务类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
public interface IUserRoleService extends IService<UserRoleDO> {



    Set<String> getRoleCodeAsStringByAccount(String account) throws Exception;

}
