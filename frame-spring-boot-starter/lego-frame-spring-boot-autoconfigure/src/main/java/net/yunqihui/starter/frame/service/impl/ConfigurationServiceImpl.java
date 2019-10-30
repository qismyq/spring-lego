package net.yunqihui.starter.frame.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.starter.frame.entity.ConfigBean;
import net.yunqihui.starter.frame.entity.Configuration;
import net.yunqihui.starter.frame.mapper.ConfigurationMapper;
import net.yunqihui.starter.frame.service.IConfigurationService;
import net.yunqihui.starter.frame.util.GlobalStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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
    public ConfigBean findParamBean() throws Exception {
        List<Configuration> list = configurationMapper.selectList(null);
        //利用反射将list对象转化为Bean
        ConfigBean config = new ConfigBean() ;
        //获取类
        Class clazz = config.getClass() ;
        Iterator<Configuration> iter = list.iterator() ;
        while(iter.hasNext()){
            Configuration configuration = iter.next() ;
            String code = configuration.getCode() ;
            String val = configuration.getValue() ;
            try {
                //获取属性
                Field field = clazz.getDeclaredField(code) ;
                //打破封装性，但是会导致java对象的属性不安全
                field.setAccessible(true);
                //给configBean对象的属性赋值
                field.set(config, val);
            }catch (Exception e){
                // 防止db加了字段但是bean忘记加属性而出错
            }

        }
        return config ;
    }


    @CachePut(value = GlobalStatic.cacheKey, key = "'configData'")
    @Override
    public ConfigBean update(Configuration config) throws Exception {
        ConfigBean configBean = null ;
        if(config != null){
            //更新数据库
            configurationMapper.updateById(config);
            configBean = findParamBean() ;
        }
        return configBean;
    }
}
