package net.yunqihui.autoconfigure.wechat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.autoconfigure.wechat.entity.PlatformsAuthInfo;
import net.yunqihui.autoconfigure.wechat.mapper.PlatformsAuthInfoMapper;
import net.yunqihui.autoconfigure.wechat.service.IPlatformsAuthInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台授权信息 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-12-13
 */
@Service
public class PlatformsAuthInfoServiceImpl extends ServiceImpl<PlatformsAuthInfoMapper, PlatformsAuthInfo> implements IPlatformsAuthInfoService {

}
