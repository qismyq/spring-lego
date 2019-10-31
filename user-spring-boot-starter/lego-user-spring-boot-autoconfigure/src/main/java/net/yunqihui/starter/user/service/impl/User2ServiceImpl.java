package net.yunqihui.starter.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.yunqihui.starter.user.entity.User;
import net.yunqihui.starter.user.mapper.UserMapper;
import net.yunqihui.starter.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/10/31 18:10
 **/
@Service("user2ServiceImpl")
public class User2ServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * @param account 账号
     * @param state   账号状态
     * @param state
     * @desc: 根据登录账号获取账号信息
     * @return: com.example.demo.model.User
     * @auther: Michael Wong
     * @email: michael_wong@yunqihui.net
     * @date: 2019/6/19 20:28
     * @update:
     */
    @Override
    public User getLoginUser(String account, String state) throws Exception {
        User user = new User().setAccount("wwww");
        return user;
    }
}
