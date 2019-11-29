package net.yunqihui.autoconfigure.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@TableName("t_user")
@ApiModel(value="User对象", description="后台用户")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "姓名")
    @TableField(condition = SqlCondition.LIKE_RIGHT)
    private String name;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "0后台管理员 1会员用户 ")
    private Integer userType;

    @ApiModelProperty(value = "是否有效,是/否")
    private String state;

    @ApiModelProperty(value = "前端用户表id")
    private Integer frontUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(exist=false)
    private String token;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
