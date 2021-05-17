package com.springlego.autoconfigure.WeChat.entity;

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
 * 
 * </p>
 *
 * @author michael wong
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("platforms_fast_register_info")
@ApiModel(value="PlatformsFastRegisterInfo对象", description="")
public class PlatformsFastRegisterInfo extends Model<PlatformsFastRegisterInfo> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品id")
    private Integer goodId ;

    @ApiModelProperty(value = "用户openId")
    private String openId ;

    @ApiModelProperty(value = "企业名")
    private String name;

    @ApiModelProperty(value = "企业代码")
    private String code;

    @ApiModelProperty(value = "企业代码类型（1：统一社会信用代码， 2：组织机构代码，3：营业执照注册号）")
    private Integer codeType;

    @ApiModelProperty(value = "法人微信")
    private String legalPersonaWechat;

    @ApiModelProperty(value = "法人姓名（绑定银行卡）")
    private String legalPersonaName;

    @ApiModelProperty(value = "创建小程序appid")
    private String appid ;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
