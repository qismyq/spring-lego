package net.yunqihui.starter.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.yunqihui.starter.user.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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

}
