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



    /**
     * @desc: 获取用户sideBar菜单
     * @param userId 用户id
     * @return: java.util.List<net.yunqihui.autoconfigure.user.entity.MenuBar>
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/16 15:22
     * @update:
     */
    List<MenuBar> getMenuBarsByUserId(Integer userId)throws Exception;

}
