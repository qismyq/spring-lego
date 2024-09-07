package com.springlego.autoconfigure.user.mapper;

import com.springlego.autoconfigure.frame.mybatis.mapper.BaseMapperX;
import com.springlego.autoconfigure.frame.mybatis.query.LambdaQueryWrapperX;
import com.springlego.autoconfigure.user.dto.dataobject.MenuDO;
import com.springlego.autoconfigure.user.dto.vo.menu.MenuListReqVO;
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
public interface MenuMapper extends BaseMapperX<MenuDO> {

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
    List<MenuDO> getMenusByUserId(@Param("userId") Integer userId, @Param("deleted") Boolean deleted, @Param("menuType") Integer menuType,@Param("pid") Long pid);

    default MenuDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(MenuDO::getParentId, parentId, MenuDO::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(MenuDO::getParentId, parentId);
    }

    default List<MenuDO> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .likeIfPresent(MenuDO::getName, reqVO.getName())
                .eqIfPresent(MenuDO::getStatus, reqVO.getStatus()));
    }

    default List<MenuDO> selectListByPermission(String permission) {
        return selectList(MenuDO::getPermission, permission);
    }
}
