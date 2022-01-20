package com.springlego.autoconfigure.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springlego.autoconfigure.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    public User getLoginUser(@Param("account") String account, @Param("state") Integer state);

}
