package com.springlego.autoconfigure.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springlego.autoconfigure.user.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * @desc: 查找用户拥有菜单
     * @param userId 用户id
     * @param state 菜单状态
     * @param menuType 菜单类型 0按钮/数据 1菜单
     * @param pid 上级菜单id
     * @return: java.util.List<Menu>
     * @auther: Michael Wong
     * @email:  michael_wang90@163.com
     * @date:   2019/12/9 15:00
     * @update:
     */
    List<Menu> getMenusByUserId(@Param("userId") Integer userId, @Param("deleted") Boolean deleted, @Param("menuType") Integer menuType,@Param("pid") Long pid);

}
