package com.springlego.autoconfigure.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import com.springlego.autoconfigure.frame.util.PageBuilder;
import com.springlego.autoconfigure.user.entity.User;
import com.springlego.autoconfigure.user.service.IUserService;
import com.springlego.autoconfigure.user.util.PasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 后台用户 前端控制器
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Api(description = "用户模块")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userServiceImpl")
    private IUserService userService ;

    @ApiOperation(value = "获取登录用户")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 1001, message = "失败"),
            @ApiResponse(code = 1002, response = User.class,message = "缺少参数") })
    @RequestMapping(value = "/getUser/json" , method = RequestMethod.GET)
    public ReturnDatas getUser(Long id)throws Exception {
        ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
        User user = userService.getLoginUser("siyuan", 1);
        returnDatas.setData(user).setMessage("成功");
        return returnDatas;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ReturnDatas list(HttpServletRequest request,User user)throws Exception {
        Page page = PageBuilder.instancePage(request);

        IPage<User> userIPage = user.selectPage(page, new QueryWrapper<User>(user));
        ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas().setMessage("成功");
        returnDatas.setPage(page);
        returnDatas.setData(userIPage);
        return returnDatas;
    }

    /**
     * @param user
     * @desc: 保存/编辑用户
     * @return: com.springlego.autoconfigure.frame.entity.ReturnDatas
     * @auther: Michael Wong
     * @email: michael_wang90@163.com
     * @date: 2020/8/18 17:57
     * @update:
     */
    @PostMapping
    public ReturnDatas save(@RequestBody User user) throws Exception {
        if (StringUtils.isNotBlank(user.getPassword())) {
            String salt = RandomStringUtils.random(8);
            user.setPassword(PasswordUtil.encrypt(user.getAccount(),user.getPassword(),salt));
            user.setSalt(salt);
        }
        user.insertOrUpdate();
        return ReturnDatas.getSuccessReturnDatas();
    }
}

