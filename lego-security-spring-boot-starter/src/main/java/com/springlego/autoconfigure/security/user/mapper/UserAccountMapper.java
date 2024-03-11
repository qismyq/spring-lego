package com.springlego.autoconfigure.security.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springlego.autoconfigure.security.user.entity.model.UserAccount;
import com.springlego.autoconfigure.security.user.entity.vo.UserAccountVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname UserAccountMapper
 * @Description UserAccountMapper接口
 * @Date 2023/1/16 上午 10:55
 * @author by H2018452
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * 根据账户查找UserAccount
     * @param account 账户
     * @param state 状态 0-禁用，1-启用
     * @param del 隐式删除
     * @return
     */
    UserAccountVO getByAccount(@Param("account") String account, @Param("state") Integer state, @Param("del") Integer del);

}
