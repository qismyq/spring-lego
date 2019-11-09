package net.yunqihui.starter.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.starter.common.entity.Configuration;
import net.yunqihui.starter.common.mapper.ConfigurationMapper;
import net.yunqihui.starter.common.service.IConfigurationService;
import net.yunqihui.starter.frame.util.GlobalStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Service
public class ConfigurationServiceImpl extends ServiceImpl<ConfigurationMapper, Configuration> implements IConfigurationService {


    @Autowired
    private ConfigurationMapper configurationMapper;

    @Override
    @Cacheable(value = GlobalStatic.cacheKey, key = "'configData'")
    public JSONObject findParamBean() throws Exception {
        List<Configuration> list = configurationMapper.selectList(null);
        Iterator<Configuration> iter = list.iterator() ;
        JSONObject jsonObject = new JSONObject();
        while(iter.hasNext()){
            Configuration configuration = iter.next() ;
            String code = configuration.getCode() ;
            String val = configuration.getValue() ;
            jsonObject.put(code, val);
        }
        return jsonObject ;
    }


    @CachePut(value = GlobalStatic.cacheKey, key = "'configData'")
    @Override
    public JSONObject update(Configuration config) throws Exception {
        JSONObject jsonObject = null ;
        if(config != null){
            //更新数据库
            configurationMapper.updateById(config);
            jsonObject = findParamBean() ;
        }
        return jsonObject;
    }
}
