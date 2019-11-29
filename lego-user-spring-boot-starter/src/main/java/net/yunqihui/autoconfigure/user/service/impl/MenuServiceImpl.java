package net.yunqihui.autoconfigure.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.autoconfigure.user.entity.Menu;
import net.yunqihui.autoconfigure.user.mapper.MenuMapper;
import net.yunqihui.autoconfigure.user.service.IMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
