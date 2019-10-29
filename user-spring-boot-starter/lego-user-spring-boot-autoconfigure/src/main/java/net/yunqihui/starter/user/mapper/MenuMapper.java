package net.yunqihui.starter.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.yunqihui.starter.user.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}
