package net.yunqihui.starter.frame.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.yunqihui.starter.frame.entity.ConfigBean;
import net.yunqihui.starter.frame.entity.Configuration;

/**
 * <p>
 * 配置表 服务类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
public interface IConfigurationService extends IService<Configuration> {


    /**
     * 获取app配置接口
     * @author wj
     * @return
     * @throws Exception
     */
    public ConfigBean findParamBean() throws Exception;

    /**
     * 新增或者修改公共参数
     * @param config
     * @return
     * @throws Exception
     */
    public ConfigBean update(Configuration config) throws  Exception ;
}
