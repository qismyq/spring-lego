package net.yunqihui.autoconfigure.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.autoconfigure.user.entity.FrontUser;
import net.yunqihui.autoconfigure.user.mapper.FrontUserMapper;
import net.yunqihui.autoconfigure.user.service.IFrontUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 前端用户表 服务实现类
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Service
public class FrontUserServiceImpl extends ServiceImpl<FrontUserMapper, FrontUser> implements IFrontUserService {

}
