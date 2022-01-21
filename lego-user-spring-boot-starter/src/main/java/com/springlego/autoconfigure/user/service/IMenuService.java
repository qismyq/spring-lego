package com.springlego.autoconfigure.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.user.entity.Menu;
import com.springlego.autoconfigure.user.entity.MenuBar;

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
     * @return: java.util.List<MenuBar>
     * @auther: Michael Wong
     * @email:  michael_wang90@163.com
     * @date:   2019/12/16 15:22
     * @update:
     */
    List<MenuBar> getMenuBarsByUserId(Integer userId)throws Exception;


    List<Long> getMenusIdByParentId(Long id) throws Exception;

    List<Long> deleteMenuAndChildrenById(Long id) throws Exception;
}
