package net.yunqihui.autoconfigure.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.yunqihui.autoconfigure.user.entity.Menu;
import net.yunqihui.autoconfigure.user.entity.MenuBar;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
public interface IMenuService extends IService<Menu> {



    List<MenuBar> getMenuBarsByUserId(Integer userId)throws Exception;

}
