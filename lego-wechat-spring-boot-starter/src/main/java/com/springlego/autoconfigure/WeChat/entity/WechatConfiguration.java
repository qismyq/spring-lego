package net.yunqihui.autoconfigure.wechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 微信配置
 * </p>
 *
 * @author michael wong
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dic_wechat_configuration")
@ApiModel(value="WechatConfiguration对象", description="微信配置")
public class WechatConfiguration extends Model<WechatConfiguration> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "配置key")
    private String code;

    @ApiModelProperty(value = "配置value")
    private String configValue;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "配置描述语")
    private String descr;

    @ApiModelProperty(value = "类型 1图片2文本3富文本编辑框4多图")
    private Integer type;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
