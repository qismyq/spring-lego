package com.springlego.autoconfigure.WeChat.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springlego.autoconfigure.WeChat.entity.WechatConfiguration;

/**
 * <p>
 * 微信配置 服务类
 * </p>
 *
 * @author michael wong
 * @since 2019-12-06
 */
public interface IWechatConfigurationService extends IService<WechatConfiguration> {

    /**
     * 获取app配置接口
     * @author wj
     * @return
     * @throws Exception
     */
    public JSONObject findParamBean() throws Exception;

}
