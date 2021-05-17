package net.yunqihui.autoconfigure.wechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 平台授权信息
 * </p>
 *
 * @author michael wong
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlatformsAuthInfo对象", description="平台授权信息")
public class PlatformsAuthInfo extends Model<PlatformsAuthInfo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "授权方 appid")
    private String authorizerAppid;

    @ApiModelProperty(value = "接口调用令牌")
    private String authorizerAccessToken;

    @ApiModelProperty(value = "调用令牌失效时间")
    private Date expiresTime;

    @ApiModelProperty(value = "接口刷新令牌")
    private String authorizerRefreshToken;

    @ApiModelProperty(value = "权限集")
    private String funcInfo;

    @ApiModelProperty(value = "快速注册信息id")
    private Integer fastRegisterId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
