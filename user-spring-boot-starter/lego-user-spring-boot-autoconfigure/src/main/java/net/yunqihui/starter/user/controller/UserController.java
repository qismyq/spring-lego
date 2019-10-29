package net.yunqihui.starter.user.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.yunqihui.starter.frame.entity.ReturnDatas;
import net.yunqihui.starter.frame.exception.ErrorMessageException;
import net.yunqihui.starter.user.entity.User;
import net.yunqihui.starter.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 后台用户 前端控制器
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService ;

    @ApiOperation(value = "获取用户列表")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 1001, message = "失败"),
            @ApiResponse(code = 1002, response = User.class,message = "缺少参数") })
    @RequestMapping(value = "/getUser/json" , method = RequestMethod.GET)
    public ReturnDatas getUser(Long id)throws Exception {
        ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
        User user = userService.getLoginUser("siyuan", "是");
        returnDatas.setData(user).setMessage("成功");
        if (user == null) {
            throw new ErrorMessageException("这是自定义异常信息");
        }
        return returnDatas;
    }

}

