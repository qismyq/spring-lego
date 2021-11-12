package com.springlego.autoconfigure.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springlego.autoconfigure.user.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 用户角色中间表 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Component
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {


    List<UserRole> getRoleCodeAsStringByAccount(String account);

}
