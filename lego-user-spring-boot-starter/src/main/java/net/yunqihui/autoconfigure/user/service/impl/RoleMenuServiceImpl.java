package net.yunqihui.autoconfigure.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.autoconfigure.user.entity.RoleMenu;
import net.yunqihui.autoconfigure.user.mapper.RoleMenuMapper;
import net.yunqihui.autoconfigure.user.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 角色菜单中间表 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<HashMap<String,String>> selectRoleMenus() {
        return roleMenuMapper.selectRoleMenus();
    }
}
