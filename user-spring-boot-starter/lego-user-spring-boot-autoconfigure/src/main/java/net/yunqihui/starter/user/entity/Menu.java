package net.yunqihui.starter.user.entity;

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

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author michael wong
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_menu")
@ApiModel(value="Menu对象", description="菜单")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    private String pid;

    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "访问地址URL")
    private String pageurl;
    @ApiModelProperty(value = "访问方式 GET POST PUT DELETE PATCH")
    private String method ;
    @ApiModelProperty(value = "0.功能按钮,1.导航菜单")
    private Integer type;

    @ApiModelProperty(value = "是否有效")
    private String state;

    @ApiModelProperty(value = "排序")
    private Integer sortno;

    private String menuIcon;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
