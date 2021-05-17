package net.yunqihui.autoconfigure.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 前端用户表
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_front_user")
@ApiModel(value="FrontUser对象", description="前端用户表")
public class FrontUser extends Model<FrontUser> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "微信登录的openid")
    @TableField("wechatId")
    private String wechatId;

    @ApiModelProperty(value = "微信登录的unionid")
    @TableField("unionId")
    private String unionId;

    @ApiModelProperty(value = "支付宝id")
    @TableField("alipayId")
    private String alipayId;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "是否弃用 0否1是")
    @TableLogic
    private Integer del;

    @ApiModelProperty(value = "前端角色")
    private Integer role;

    @ApiModelProperty(value = "jwttoken")
    @TableField(exist = false)
    private String token;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
