package net.yunqihui.autoconfigure.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.autoconfigure.common.mapper.AlarmEmailMapper;
import net.yunqihui.autoconfigure.common.entity.AlarmEmail;
import net.yunqihui.autoconfigure.common.service.IAlarmEmailService;
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
