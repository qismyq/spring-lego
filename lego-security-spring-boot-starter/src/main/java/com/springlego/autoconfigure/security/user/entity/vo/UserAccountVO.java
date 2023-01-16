package com.springlego.autoconfigure.security.user.entity.vo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Classname UserAccountVO
 * @Description 系统用户账户VO
 * @Date 2023/1/16 上午 09:57
 * @author  by H2018452
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class UserAccountVO {

    private static final long serialVersionUID = 1L;

    private Long id;


    private String name;

    /**
     * 账号
     */
    @Alias("username")
    private String account;


    /**
     * 密码
     */
    private String password;

    /**
     * 是否有效
     */
    private Integer state;


    private Integer del;
}
