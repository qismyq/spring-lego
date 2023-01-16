package com.springlego.autoconfigure.security.user.entity.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Classname UserAccount
 * @Description 系统用户账户表
 * @Date 2023/1/16 上午 09:57
 * @author  by H2018452
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("u_user_account")
public class UserAccount extends Model<UserAccount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField(condition = SqlCondition.LIKE_RIGHT)
    private String name;

    /**
     * 账号
     */
    private String account;


    /**
     * 密码
     */
    private String password;

    /**
     * 是否有效
     */
    private Integer state;


    private int del;
}
