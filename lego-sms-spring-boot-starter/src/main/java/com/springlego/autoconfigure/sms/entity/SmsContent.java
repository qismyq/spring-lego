package com.springlego.autoconfigure.sms.entity;

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
 * 短信模板
 * </p>
 *
 * @author michael wong
 * @since 2020-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dic_sms_content")
@ApiModel(value = "SmsContent对象", description = "短信模板")
public class SmsContent extends Model<SmsContent> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "是否校验手机号已存在 0不校验1不能存在2必须存在")
    private Integer verify;

    @ApiModelProperty(value = "短信类型 描述语")
    private String type;

    @ApiModelProperty(value = "短信模板ID")
    private String templateCode;

    @ApiModelProperty(value = "是否弃用 0否1是")
    @TableLogic
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
