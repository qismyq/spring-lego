package net.yunqihui.autoconfigure.user.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.autoconfigure.frame.entity.ReturnDatas;
import net.yunqihui.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
import net.yunqihui.autoconfigure.user.entity.Menu;
import net.yunqihui.autoconfigure.user.entity.MenuBar;
import net.yunqihui.autoconfigure.user.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 菜单控制层
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/9 15:56
 **/
@Api(description = "菜单模块")
@Slf4j
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private IMenuService menuService;


    @ApiOperation(value = "全部菜单", notes = "获取所有菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header", name = "appId", value = "账户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="header", name = "authorization", value = "token", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40203,message="无访问权限"),
            @ApiResponse(code = 40204,message="认证失效，请重新登录")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ReturnDatas list()throws Exception {

        List<Menu> list = menuService.list();

        return ReturnDatas.getSuccessReturnDatas().setData(list);
    }



    @ApiOperation(value = "用户菜单", notes = "获取登录用户菜单sideBar")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "userId", value = "用户id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="header", name = "appId", value = "账户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="header", name = "authorization", value = "token", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40203,message="无访问权限"),
            @ApiResponse(code = 40204,message="认证失效，请重新登录")
    })
    @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET)
    public ReturnDatas getMenuByUserId(@PathVariable("userId") Integer userId)throws Exception {

        if (userId == null) {
            return ReturnDatas.getErrorReturnDatas(FrameErrorCodeEnum.E_40000);
        }

        List<MenuBar> menuBars = menuService.getMenuBarsByUserId(userId);

        return ReturnDatas.getSuccessReturnDatas().setData(menuBars);
    }


//    @Autowired
//    IUserRoleService userRoleService;
//    @Autowired
//    IRoleMenuService roleMenuService;


    @ApiOperation(value = "添加菜单")
    @RequestMapping(method = RequestMethod.POST)
    public ReturnDatas save(@RequestBody Menu menu)throws Exception {


        menuService.saveOrUpdate(menu);

//        UserRole userRole = new UserRole().setUserId(1L).setRoleId(3);
//
//        userRoleService.save(userRole);
//
//        RoleMenu roleMenu = new RoleMenu().setMenuId(menu.getId()).setRoleId(3L);

//        roleMenuService.save(roleMenu);

        return ReturnDatas.getSuccessReturnDatas().setData(menu);
    }

    @ApiOperation(value = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name = "id", value = "菜单id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType="header", name = "appId", value = "账户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="header", name = "authorization", value = "token", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 40203,message="无访问权限"),
            @ApiResponse(code = 40204,message="认证失效，请重新登录")
    })
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ReturnDatas delete(@PathVariable("id") Long id)throws Exception {

        List<Long> ids = menuService.deleteMenuAndChildrenById(id);

        return ReturnDatas.getSuccessReturnDatas().setData(ids);
    }
}
