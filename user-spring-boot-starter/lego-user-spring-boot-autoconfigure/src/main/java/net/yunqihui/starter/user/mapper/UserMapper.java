package net.yunqihui.starter.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.yunqihui.starter.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 后台用户 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Component
@Mapper
public interface UserMapper extends BaseMapper<User> {


    public User getLoginUser(@Param("account") String account, @Param("state") String state);

}
