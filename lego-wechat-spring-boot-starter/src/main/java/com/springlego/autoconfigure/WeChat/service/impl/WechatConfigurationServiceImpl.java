package com.springlego.autoconfigure.WeChat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.WeChat.service.IWechatConfigurationService;
import com.springlego.autoconfigure.WeChat.entity.WeChatStatic;
import com.springlego.autoconfigure.WeChat.entity.WechatConfiguration;
import com.springlego.autoconfigure.WeChat.mapper.WechatConfigurationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 微信配置 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-12-06
 */
@Service
public class WechatConfigurationServiceImpl extends ServiceImpl<WechatConfigurationMapper, WechatConfiguration> implements IWechatConfigurationService {


    @Autowired
    private WechatConfigurationMapper wechatConfigurationMapper;

    @Override
    @Cacheable(value = WeChatStatic.WECHAT_CACHE_SPACE, key = "'wechatConfigCache'")
    public JSONObject findParamBean() throws Exception {
        List<WechatConfiguration> list = wechatConfigurationMapper.selectList(null);
        Iterator<WechatConfiguration> iter = list.iterator() ;
        JSONObject jsonObject = new JSONObject();
        while(iter.hasNext()){
            WechatConfiguration configuration = iter.next() ;
            String code = configuration.getCode() ;
            String val = configuration.getConfigValue() ;
            jsonObject.put(code, val);
        }
        return jsonObject ;
    }
}
