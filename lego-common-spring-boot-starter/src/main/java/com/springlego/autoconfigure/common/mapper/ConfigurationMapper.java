package com.springlego.autoconfigure.common.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springlego.autoconfigure.common.entity.Configuration;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 配置表 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Mapper
public interface ConfigurationMapper extends BaseMapper<Configuration> {

}
