package com.springlego.autoconfigure.user.controller;

import cn.hutool.core.collection.CollUtil;
import com.springlego.autoconfigure.common.enums.CommonStatusEnum;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import com.springlego.autoconfigure.security.util.SecurityUtils;
import com.springlego.autoconfigure.user.dto.dataobject.MenuDO;
import com.springlego.autoconfigure.user.dto.dataobject.RoleDO;
import com.springlego.autoconfigure.user.dto.dataobject.UserAccountDO;
import com.springlego.autoconfigure.user.dto.vo.AuthPermissionInfoRespVO;
import com.springlego.autoconfigure.user.service.IRoleService;
import com.springlego.autoconfigure.user.service.IUserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author by michael_wang
 * @Classname AuthController
 * @Description TODO
 * @Date 2024/8/31 上午 09:19
 */
@Tag(name = "管理后台 - 认证")
@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private IUserAccountService userAccountService;
    @Resource
    private IRoleService roleService;


    @GetMapping("/get-permission-info")
    @Operation(summary = "获取登录用户的权限信息")
    public ReturnDatas<AuthPermissionInfoRespVO> getPermissionInfo() throws Exception {
        // 1.1 获得用户信息
        UserAccountDO user = userAccountService.getUserById(SecurityUtils.getUserId());
        if (user == null) {
            return ReturnDatas.getSuccessReturnDatas(null);
        }

        // 1.2 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(SecurityUtils.getUserId());
        if (CollUtil.isEmpty(roleIds)) {
            return ReturnDatas.getSuccessReturnDatas(AuthConvert.INSTANCE.convert(user, Collections.emptyList(), Collections.emptyList()));
        }
        List<RoleDO> roles = roleService.getRoleList(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())); // 移除禁用的角色

        // 1.3 获得菜单列表
        Set<Long> menuIds = permissionService.getRoleMenuListByRoleId(convertSet(roles, RoleDO::getId));
        List<MenuDO> menuList = menuService.getMenuList(menuIds);
        menuList.removeIf(menu -> !CommonStatusEnum.ENABLE.getStatus().equals(menu.getStatus())); // 移除禁用的菜单

        // 2. 拼接结果返回
        return ReturnDatas.getSuccessReturnDatas(AuthConvert.INSTANCE.convert(user, roles, menuList));
    }
}
