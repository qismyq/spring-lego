package net.yunqihui.starter.frame.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 配置表
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dic_configuration")
@ApiModel(value="Configuration对象", description="配置表")
public class Configuration extends Model<Configuration> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统表的KEY")
    @TableId(value = "code", type = IdType.AUTO)
    private String code;

    @ApiModelProperty(value = "系统表的值")
    private String value;

    @ApiModelProperty(value = "描述")
    private String descr;

    @ApiModelProperty(value = "是否使用(Y是N否)")
    @TableField("chooseFlag")
    private String chooseFlag;

    private Integer id;

    @ApiModelProperty(value = "类型：1图片2文本3富文本编辑框4多图")
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
