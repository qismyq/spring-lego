package com.springlego.autoconfigure.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Delete;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台用户
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("u_user_account")
//@ApiModel(value="User对象", description="后台用户")
public class UserAccount extends Model<UserAccount> {

    private static final long serialVersionUID=1L;

//    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

//    @ApiModelProperty(value = "账号")
    private String account;

//    @ApiModelProperty(value = "姓名")
    @TableField(condition = SqlCondition.LIKE_RIGHT)
    private String name;

//    @ApiModelProperty(value = "密码")
    private String password;

//    @ApiModelProperty(value = "密码盐")
    private String salt;

//    @ApiModelProperty(value = "头像")
    private String avatar;

//    @ApiModelProperty(value = "性别")
    private Integer sex;

//    @ApiModelProperty(value = "手机号码")
    private String mobile;

//    @ApiModelProperty(value = "邮箱")
    private String email;

//    @ApiModelProperty(value = "是否有效,是/否")
    private String state;

//    @ApiModelProperty(value = "前端用户表id")
    private Integer frontUserId;

//    @ApiModelProperty(value = "创建时间")
    private Date createTime;
//    @ApiModelProperty(value = "创建人")
    private String creator;
//    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
//    @ApiModelProperty(value = "更新人")
    private String updater;
//    @ApiModelProperty(value = "删除状态（0否1是）")
    @TableLogic
    private Integer deleted;

    @TableField(exist=false)
    private String token;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
