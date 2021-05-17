package com.springlego.autoconfigure.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.user.mapper.UserRoleMapper;
import com.springlego.autoconfigure.user.service.IUserRoleService;
import com.springlego.autoconfigure.user.entity.UserRole;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户角色中间表 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRoleMapper userRoleMapper;

//    @Cacheable(value = GlobalStatic.qxCacheKey,key = "'getRolesAsString_'+#account")
    @Override
    public Set<String> getRoleCodeAsStringByAccount(String account)throws Exception {
        if (StringUtils.isBlank(account)) {
            return null;
        }
        List<UserRole> userRoles = userRoleMapper.getRoleCodeAsStringByAccount(account);
        if (CollectionUtils.isNotEmpty(userRoles)) {
            Set<String> rolesCode = new HashSet<>(userRoles.size());
            for (UserRole userRole : userRoles) {
                rolesCode.add(userRole.getRoleCode());
            }
            return rolesCode;
        }
        return null;
    }
}
