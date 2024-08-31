package com.springlego.autoconfigure.common.dto.vo.dict;

import com.springlego.autoconfigure.common.enums.CommonStatusEnum;
import com.springlego.autoconfigure.common.validation.InEnum;
import com.springlego.autoconfigure.frame.entity.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Schema(description = "管理后台 - 字典类型分页列表 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataPageReqVO extends PageParam {

    @Schema(description = "字典标签", example = "芋道")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

    @Schema(description = "字典类型，模糊匹配", example = "sys_common_sex")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String dictType;

    @Schema(description = "展示状态，参见 CommonStatusEnum 枚举类", example = "1")
    @InEnum(value = CommonStatusEnum.class, message = "修改状态必须是 {value}")
    private Integer status;

}
