package com.springlego.autoconfigure.common.controller.dict;

import com.springlego.autoconfigure.common.dto.dataobject.dict.DictTypeDO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictTypePageReqVO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictTypeRespVO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictTypeSaveReqVO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictTypeSimpleRespVO;
import com.springlego.autoconfigure.common.service.dict.IDictTypeService;
import com.springlego.autoconfigure.common.util.BeanUtils;
import com.springlego.autoconfigure.frame.entity.PageResult;
import com.springlego.autoconfigure.frame.entity.ReturnDatas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台 - 字典类型")
@RestController
@RequestMapping("/system/dict-type")
@Validated
public class DictTypeController {

    @Resource
    private IDictTypeService dictTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建字典类型")
    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public ReturnDatas<Long> createDictType(@Valid @RequestBody DictTypeSaveReqVO createReqVO) {
        Long dictTypeId = dictTypeService.createDictType(createReqVO);
        return ReturnDatas.getSuccessReturnDatas(dictTypeId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改字典类型")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public ReturnDatas<Boolean> updateDictType(@Valid @RequestBody DictTypeSaveReqVO updateReqVO) {
        dictTypeService.updateDictType(updateReqVO);
        return ReturnDatas.getSuccessReturnDatas(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除字典类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public ReturnDatas<Boolean> deleteDictType(Long id) {
        dictTypeService.deleteDictType(id);
        return ReturnDatas.getSuccessReturnDatas(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典类型的分页列表")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public ReturnDatas<PageResult<DictTypeRespVO>> pageDictTypes(@Valid DictTypePageReqVO pageReqVO) {
        PageResult<DictTypeDO> pageResult = dictTypeService.getDictTypePage(pageReqVO);
        return ReturnDatas.getSuccessReturnDatas(BeanUtils.toBean(pageResult, DictTypeRespVO.class));
    }

    @Operation(summary = "/查询字典类型详细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @GetMapping(value = "/get")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public ReturnDatas<DictTypeRespVO> getDictType(@RequestParam("id") Long id) {
        DictTypeDO dictType = dictTypeService.getDictType(id);
        return ReturnDatas.getSuccessReturnDatas(BeanUtils.toBean(dictType, DictTypeRespVO.class));
    }

    @GetMapping(value = {"/list-all-simple", "simple-list"})
    @Operation(summary = "获得全部字典类型列表", description = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
    // 无需添加权限认证，因为前端全局都需要
    public ReturnDatas<List<DictTypeSimpleRespVO>> getSimpleDictTypeList() {
        List<DictTypeDO> list = dictTypeService.getDictTypeList();
        return ReturnDatas.getSuccessReturnDatas(BeanUtils.toBean(list, DictTypeSimpleRespVO.class));
    }


}
