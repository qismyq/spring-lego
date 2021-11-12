package com.springlego.starter.social.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springlego.starter.social.entity.User;
import com.springlego.starter.social.mapper.UserMapper;
import com.springlego.starter.social.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Description 用户业务实现层
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/6/19 20:04
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {


}


