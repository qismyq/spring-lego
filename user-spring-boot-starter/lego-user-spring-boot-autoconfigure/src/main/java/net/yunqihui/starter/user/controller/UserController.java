package net.yunqihui.starter.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.yunqihui.starter.frame.entity.ReturnDatas;
import net.yunqihui.starter.frame.util.PageBuilder;
import net.yunqihui.starter.user.entity.User;
import net.yunqihui.starter.user.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        User user = userService.getLoginUser("siyuan", "是");
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

}

