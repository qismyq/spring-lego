package com.springlego.autoconfigure.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springlego.autoconfigure.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {


}
