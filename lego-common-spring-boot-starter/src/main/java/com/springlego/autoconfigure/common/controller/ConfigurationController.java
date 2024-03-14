package com.springlego.autoconfigure.common.controller;


import com.alibaba.fastjson.JSONObject;
import com.springlego.autoconfigure.common.entity.Configuration;
import com.springlego.autoconfigure.common.service.IConfigurationService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//@Api(description = "基础配置模块")
@RestController
@CrossOrigin
@RequestMapping(value = "/configurations")
public class ConfigurationController {


    @Autowired
    private IConfigurationService configurationService;

//    @ApiOperation(value = "获取配置信息")
//    @ApiResponses(value = { @ApiResponse(code = 0, message = "成功"),
//            @ApiResponse(code = 1, message = "失败"),
//            @ApiResponse(code = 40000, message = "参数缺失"),
//            @ApiResponse(code = 50000,message = "缺少参数") })
    @RequestMapping(method = RequestMethod.GET)
    public ReturnDatas listjson() throws Exception {
        ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

        JSONObject datas = configurationService.findParamBean();

        returnObject.setData(datas);
        return returnObject;
    }


    /**
     * 修改 操作
     *
     */
//    @ApiOperation(value = "修改配置信息")
//    @ApiResponses(value = { @ApiResponse(code = 0, message = "成功"),
//            @ApiResponse(code = 1, message = "失败"),
//            @ApiResponse(code = 40000, message = "参数缺失"),
//            @ApiResponse(code = 50000,message = "缺少参数") })
    @RequestMapping(method = RequestMethod.PUT)
    public ReturnDatas saveorupdate(@RequestBody Configuration configuration, HttpServletRequest request, HttpServletResponse response) throws Exception{
        ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

        String code =configuration.getCode();
        if(StringUtils.isBlank(code)){
            configuration.setCode(null);
        }
        configurationService.update(configuration);
        return returnObject;

    }

}

