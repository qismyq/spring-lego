package net.yunqihui.starter.common.entity;

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
 * 警报邮箱
 * </p>
 *
 * @author michael wong
 * @since 2019-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dic_alarmEmail")
@ApiModel(value="AlarmEmail对象", description="警报邮箱")
public class AlarmEmail extends Model<AlarmEmail> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "邮箱地址")
    @TableField("emaiAddress")
    private String emaiAddress;

    @ApiModelProperty(value = "收件人名称")
    private String receiver;

    @ApiModelProperty(value = "1收件人 2抄送人")
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
