package com.springlego.autoconfigure.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.user.dto.dataobject.RoleMenuDO;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 角色菜单中间表 服务类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
public interface IRoleMenuService extends IService<RoleMenuDO> {

    public List<HashMap<String,String>> selectRoleMenus();
}
