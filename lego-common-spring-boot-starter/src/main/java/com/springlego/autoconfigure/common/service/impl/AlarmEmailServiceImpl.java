package com.springlego.autoconfigure.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.common.mapper.AlarmEmailMapper;
import com.springlego.autoconfigure.common.service.IAlarmEmailService;
import com.springlego.autoconfigure.common.entity.AlarmEmail;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 警报邮箱 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Service
public class AlarmEmailServiceImpl extends ServiceImpl<AlarmEmailMapper, AlarmEmail> implements IAlarmEmailService {

}
