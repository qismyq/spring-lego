package com.springlego.autoconfigure.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springlego.autoconfigure.sms.entity.SmsContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 短信模板 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2020-07-13
 */
@Mapper
public interface SmsContentMapper extends BaseMapper<SmsContent> {

}
