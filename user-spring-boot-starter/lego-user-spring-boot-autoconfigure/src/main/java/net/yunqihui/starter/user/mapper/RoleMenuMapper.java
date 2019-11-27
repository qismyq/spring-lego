package net.yunqihui.starter.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.yunqihui.starter.user.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 角色菜单中间表 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    public List<HashMap<String,String>> selectRoleMenus();
}
