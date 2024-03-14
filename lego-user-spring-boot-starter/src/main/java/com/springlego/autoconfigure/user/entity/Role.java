package com.springlego.autoconfigure.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_role")
//@ApiModel(value="Role对象", description="角色")
public class Role extends Model<Role> {

    private static final long serialVersionUID=1L;

//    @ApiModelProperty(value = "角色ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty(value = "角色名称")
    private String name;

//    @ApiModelProperty(value = "权限编码")
    private String code;

//    @ApiModelProperty(value = "上级角色ID")
    private Integer pid;

//    @ApiModelProperty(value = "备注")
    private String remark;

//    @ApiModelProperty(value = "是否有效(否/是)")
    private String state;

//    @ApiModelProperty(value = "特殊角色(0否/1是)")
    private Integer special;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
