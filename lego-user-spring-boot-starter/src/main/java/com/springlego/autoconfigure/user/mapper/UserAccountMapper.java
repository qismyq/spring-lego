package com.springlego.autoconfigure.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springlego.autoconfigure.frame.mybatis.mapper.BaseMapperX;
import com.springlego.autoconfigure.user.dto.dataobject.UserAccountDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台用户 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Mapper
@Repository("u_userAccountMapper")
public interface UserAccountMapper extends BaseMapperX<UserAccountDO> {


    public UserAccountDO getLoginUser(@Param("account") String account, @Param("state") Integer state);

}
