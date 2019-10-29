package net.yunqihui.starter.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.starter.user.entity.UserRole;
import net.yunqihui.starter.user.mapper.UserRoleMapper;
import net.yunqihui.starter.user.service.IUserRoleService;
import org.springframework.stereotype.Service;

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

}
