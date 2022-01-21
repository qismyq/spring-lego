package com.springlego.autoconfigure.user.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 菜单栏VO
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/12/9 17:23
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MenuBar implements Serializable {
    private static final long serialVersionUID=1L;

    private Long id;

    private String path ;

    private String component;

    private String name ;

    private JSONObject meta;

    private List<MenuBar> children ;


}
