package com.springlego.autoconfigure.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.user.entity.Role;
import com.springlego.autoconfigure.user.mapper.RoleMapper;
import com.springlego.autoconfigure.user.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
