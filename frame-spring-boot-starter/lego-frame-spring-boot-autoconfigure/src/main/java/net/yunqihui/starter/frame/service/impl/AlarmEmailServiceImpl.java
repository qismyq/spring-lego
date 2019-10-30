package net.yunqihui.starter.frame.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.starter.frame.entity.AlarmEmail;
import net.yunqihui.starter.frame.mapper.AlarmEmailMapper;
import net.yunqihui.starter.frame.service.IAlarmEmailService;
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
