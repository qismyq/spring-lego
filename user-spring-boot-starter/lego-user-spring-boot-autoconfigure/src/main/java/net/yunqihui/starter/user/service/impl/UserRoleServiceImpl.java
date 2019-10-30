package net.yunqihui.starter.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.starter.frame.util.GlobalStatic;
import net.yunqihui.starter.user.entity.UserRole;
import net.yunqihui.starter.user.mapper.UserRoleMapper;
import net.yunqihui.starter.user.service.IUserRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = GlobalStatic.qxCacheKey,key = "'getRolesAsString_'+#userId")
    @Override
    public Set<String> getRoleCodeAsString(Long userId) throws Exception {
        if (userId == null) {
            return null;
        }
        List<UserRole> userRoles = userRoleMapper.getRoleCodeAsString(userId);
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
