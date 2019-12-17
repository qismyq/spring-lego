package net.yunqihui.autoconfigure.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.autoconfigure.user.entity.Menu;
import net.yunqihui.autoconfigure.user.entity.MenuBar;
import net.yunqihui.autoconfigure.user.mapper.MenuMapper;
import net.yunqihui.autoconfigure.user.service.IMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    MenuMapper menuMapper;


    @Override
    public List<MenuBar> getMenuBarsByUserId(Integer userId) throws Exception {

        List<MenuBar> menuList = new ArrayList<>();

        List<Menu> topMenuBars = menuMapper.getMenusByUserId(userId, "是", 1,0L);
        if (CollectionUtils.isNotEmpty(topMenuBars)) {

            // 顶级菜单循环
            topMenuBars.stream().forEach(menu -> {

                MenuBar topMenu = menuToMenuBar(menu,true);

                // 子菜单获取
                List<Menu> childrenMenus = menuMapper.getMenusByUserId(userId, "是", 1, menu.getId());
                List<MenuBar> childrenMenuBars = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(childrenMenus)) {
                    childrenMenus.stream().forEach(childMenu -> {
                        childrenMenuBars.add(menuToMenuBar(childMenu, false));
                    });
                }else {
                    childrenMenuBars.add(menuToMenuBar(menu, false));
                    // 如果无下级，则置空meta，可以进行点击展示右侧的操作
                    topMenu.setMeta(null);
                }

                topMenu.setChildren(childrenMenuBars);

                menuList.add(topMenu);
            });
        }

        return menuList;
    }


    private MenuBar menuToMenuBar(Menu menu,boolean top) {
        if (menu == null) {
            return null;
        }
        MenuBar menuBar = new MenuBar();
        if (top) {
            menuBar.setComponent("Layout")
                    .setPath(menu.getTopRouter());
        }else {
            menuBar.setComponent(menu.getComponent())
                    .setPath(menu.getRouter());
        }
        JSONObject metaJson = new JSONObject();
        metaJson.put("icon", menu.getIcon());
        metaJson.put("title", menu.getTitle());
        menuBar.setMeta(metaJson)
                .setName(menu.getName());

        return menuBar;
    }
}
