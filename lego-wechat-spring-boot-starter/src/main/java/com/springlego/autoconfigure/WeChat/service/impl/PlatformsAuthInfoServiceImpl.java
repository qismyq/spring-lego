package com.springlego.autoconfigure.WeChat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.autoconfigure.WeChat.entity.PlatformsAuthInfo;
import com.springlego.autoconfigure.WeChat.mapper.PlatformsAuthInfoMapper;
import com.springlego.autoconfigure.WeChat.service.IPlatformsAuthInfoService;
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
