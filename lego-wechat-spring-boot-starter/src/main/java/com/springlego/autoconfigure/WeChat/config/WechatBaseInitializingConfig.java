package com.springlego.autoconfigure.WeChat.config;

import com.alibaba.fastjson.JSONObject;
import com.springlego.autoconfigure.WeChat.service.IWechatConfigurationService;
import lombok.extern.slf4j.Slf4j;
import com.springlego.autoconfigure.WeChat.entity.WeChatStatic;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by Michael on 2017/6/27.
 * 初始化就加载config内容到缓存中
 */
@Slf4j
@Service
public class WechatBaseInitializingConfig implements InitializingBean {

    @Autowired
    IWechatConfigurationService configurationService;
    @Autowired
    private CacheManager cacheManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("**************init wechat config data***************");
        // 配置表信息以及系统表信息
        JSONObject config = configurationService.findParamBean();
        Cache cache = cacheManager.getCache(WeChatStatic.WECHAT_CACHE_SPACE);
        cache.put(WeChatStatic.WECHAT_CONFIG_CACHE, config);

    }
}
