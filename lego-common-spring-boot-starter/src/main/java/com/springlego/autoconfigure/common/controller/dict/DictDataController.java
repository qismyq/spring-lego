package com.springlego.autoconfigure.common.controller.dict;

import com.springlego.autoconfigure.common.dto.dataobject.dict.DictDataDO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictDataPageReqVO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictDataRespVO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictDataSaveReqVO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictDataSimpleRespVO;
import com.springlego.autoconfigure.common.enums.CommonStatusEnum;
import com.springlego.autoconfigure.common.service.dict.IDictDataService;
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


@Tag(name = "管理后台 - 字典数据")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class DictDataController {

    @Resource
    private IDictDataService dictDataService;

    @PostMapping("/create")
    @Operation(summary = "新增字典数据")
    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public ReturnDatas createDictData(@Valid @RequestBody DictDataSaveReqVO createReqVO) {
        Long dictDataId = dictDataService.createDictData(createReqVO);
        return ReturnDatas.getSuccessReturnDatas(dictDataId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改字典数据")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public ReturnDatas<Boolean> updateDictData(@Valid @RequestBody DictDataSaveReqVO updateReqVO) {
        dictDataService.updateDictData(updateReqVO);
        return ReturnDatas.getSuccessReturnDatas();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除字典数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public ReturnDatas<Boolean> deleteDictData(Long id) {
        dictDataService.deleteDictData(id);
        return ReturnDatas.getSuccessReturnDatas();
    }

    @GetMapping(value = {"/list-all-simple", "simple-list"})
    @Operation(summary = "获得全部字典数据列表", description = "一般用于管理后台缓存字典数据在本地")
    // 无需添加权限认证，因为前端全局都需要
    public ReturnDatas<List<DictDataSimpleRespVO>> getSimpleDictDataList() {
        List<DictDataDO> list = dictDataService.getDictDataList(
                CommonStatusEnum.ENABLE.getStatus(), null);
        return ReturnDatas.getSuccessReturnDatas(BeanUtils.toBean(list, DictDataSimpleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "/获得字典类型的分页列表")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public ReturnDatas getDictTypePage(@Valid DictDataPageReqVO pageReqVO) {
        PageResult<DictDataDO> pageResult = dictDataService.getDictDataPage(pageReqVO);
        return ReturnDatas.getSuccessReturnDatas(BeanUtils.toBean(pageResult, DictDataRespVO.class));
    }

    @GetMapping(value = "/get")
    @Operation(summary = "/查询字典数据详细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public ReturnDatas<DictDataRespVO> getDictData(@RequestParam("id") Long id) {
        DictDataDO dictData = dictDataService.getDictData(id);
        return ReturnDatas.getSuccessReturnDatas(BeanUtils.toBean(dictData, DictDataRespVO.class));
    }


}
