package com.springlego.autoconfigure.common.service.dict.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.springlego.autoconfigure.common.entity.dataobject.dict.DictDataDO;
import com.springlego.autoconfigure.common.entity.dataobject.dict.DictTypeDO;
import com.springlego.autoconfigure.common.entity.vo.dict.DictDataPageReqVO;
import com.springlego.autoconfigure.common.entity.vo.dict.DictDataSaveReqVO;
import com.springlego.autoconfigure.common.enums.CommonStatusEnum;
import com.springlego.autoconfigure.common.enums.CommonErrorCodeEnum;
import com.springlego.autoconfigure.common.mapper.dict.DictDataMapper;
import com.springlego.autoconfigure.common.service.dict.IDictDataService;
import com.springlego.autoconfigure.common.service.dict.IDictTypeService;
import com.springlego.autoconfigure.common.util.CollectionUtils;
import com.springlego.autoconfigure.frame.entity.PageResult;
import com.springlego.autoconfigure.frame.errorhandler.ErrorMessageException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * 字典数据 Service 实现类
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class DictDataServiceImpl implements IDictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<DictDataDO> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(DictDataDO::getDictType)
            .thenComparingInt(DictDataDO::getSort);

    @Resource
    private IDictTypeService dictTypeService;

    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictDataDO> getDictDataList(Integer status, String dictType) {
        List<DictDataDO> list = dictDataMapper.selectListByStatusAndDictType(status, dictType);
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public PageResult<DictDataDO> getDictDataPage(DictDataPageReqVO pageReqVO) {
        return dictDataMapper.selectPage(pageReqVO);
    }

    @Override
    public DictDataDO getDictData(Long id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public Long createDictData(DictDataSaveReqVO createReqVO) {
        // 校验字典类型有效
        validateDictTypeExists(createReqVO.getDictType());
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(null, createReqVO.getDictType(), createReqVO.getValue());

        // 插入字典类型
        DictDataDO dictData = BeanUtil.toBean(createReqVO, DictDataDO.class);
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void updateDictData(DictDataSaveReqVO updateReqVO) {
        // 校验自己存在
        validateDictDataExists(updateReqVO.getId());
        // 校验字典类型有效
        validateDictTypeExists(updateReqVO.getDictType());
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(updateReqVO.getId(), updateReqVO.getDictType(), updateReqVO.getValue());

        // 更新字典类型
        DictDataDO updateObj = BeanUtil.toBean(updateReqVO, DictDataDO.class);
        dictDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictData(Long id) {
        // 校验是否存在
        validateDictDataExists(id);

        // 删除字典数据
        dictDataMapper.deleteById(id);
    }

    @Override
    public long getDictDataCountByDictType(String dictType) {
        return dictDataMapper.selectCountByDictType(dictType);
    }

    @VisibleForTesting
    public void validateDictDataValueUnique(Long id, String dictType, String value) {
        DictDataDO dictData = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw new ErrorMessageException(CommonErrorCodeEnum.VALUE_DUPLICATE_ERROR);
        }
        if (!dictData.getId().equals(id)) {
            throw new ErrorMessageException(CommonErrorCodeEnum.VALUE_DUPLICATE_ERROR);
        }
    }

    @VisibleForTesting
    public void validateDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        DictDataDO dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw new ErrorMessageException(CommonErrorCodeEnum.DATA_NOT_EXISTS_ERROR);
        }
    }

    @VisibleForTesting
    public void validateDictTypeExists(String type) {
        DictTypeDO dictType = dictTypeService.getDictType(type);
        if (dictType == null) {
            throw new ErrorMessageException(CommonErrorCodeEnum.DATA_NOT_EXISTS_ERROR);
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
            throw new ErrorMessageException(CommonErrorCodeEnum.STATUS_NOT_ENABLED_ERROR);
        }
    }

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        if (CollUtil.isEmpty(values)) {
            return;
        }
        Map<String, DictDataDO> dictDataMap = CollectionUtils.convertMap(
                dictDataMapper.selectByDictTypeAndValues(dictType, values), DictDataDO::getValue);
        // 校验
        values.forEach(value -> {
            DictDataDO dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw new ErrorMessageException(CommonErrorCodeEnum.DATA_NOT_EXISTS_ERROR);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dictData.getStatus())) {
                throw new ErrorMessageException(CommonErrorCodeEnum.STATUS_NOT_ENABLED_ERROR);
            }
        });
    }

    @Override
    public DictDataDO getDictData(String dictType, String value) {
        return dictDataMapper.selectByDictTypeAndValue(dictType, value);
    }

    @Override
    public DictDataDO parseDictData(String dictType, String label) {
        return dictDataMapper.selectByDictTypeAndLabel(dictType, label);
    }

}
