package net.yunqihui.starter.frame.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.yunqihui.starter.frame.entity.Configuration;
import net.yunqihui.starter.frame.entity.ReturnDatas;
import net.yunqihui.starter.frame.service.IConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 配置表 前端控制器
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Api(description = "用户模块")
@RestController
@RequestMapping("/configuration")
public class ConfigurationController {


    @Autowired
    private IConfigurationService configurationService;

    @ApiOperation(value = "获取配置信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 1001, message = "失败"),
            @ApiResponse(code = 1002, response = ReturnDatas.class,message = "缺少参数") })
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ReturnDatas listjson(HttpServletRequest request, Model model, Configuration configuration) throws Exception {
        ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

        JSONObject datas = configurationService.findParamBean();

        returnObject.setData(datas);
        return returnObject;
    }


    /**
     * 修改 操作
     *
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ReturnDatas saveorupdate(Model model,Configuration configuration,HttpServletRequest request,HttpServletResponse response) throws Exception{
        ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

        String code =configuration.getCode();
        if(StringUtils.isBlank(code)){
            configuration.setCode(null);
        }
        configurationService.update(configuration);
        return returnObject;

    }

}

