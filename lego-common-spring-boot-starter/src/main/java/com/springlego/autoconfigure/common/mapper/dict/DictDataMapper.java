package com.springlego.autoconfigure.common.mapper.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springlego.autoconfigure.common.dto.dataobject.dict.DictDataDO;
import com.springlego.autoconfigure.common.dto.vo.dict.DictDataPageReqVO;
import com.springlego.autoconfigure.frame.entity.PageResult;
import com.springlego.autoconfigure.frame.mybatis.mapper.BaseMapperX;
import com.springlego.autoconfigure.frame.mybatis.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Mapper
public interface DictDataMapper extends BaseMapperX<DictDataDO> {

    default DictDataDO selectByDictTypeAndValue(String dictType, String value) {
        return selectOne(DictDataDO::getDictType, dictType, DictDataDO::getValue, value);
    }

    default DictDataDO selectByDictTypeAndLabel(String dictType, String label) {
        return selectOne(DictDataDO::getDictType, dictType, DictDataDO::getLabel, label);
    }

    default List<DictDataDO> selectByDictTypeAndValues(String dictType, Collection<String> values) {
        return selectList(new LambdaQueryWrapper<DictDataDO>().eq(DictDataDO::getDictType, dictType)
                .in(DictDataDO::getValue, values));
    }

    default long selectCountByDictType(String dictType) {
        return selectCount(DictDataDO::getDictType, dictType);
    }

    default PageResult<DictDataDO> selectPage(DictDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictDataDO>()
                .likeIfPresent(DictDataDO::getLabel, reqVO.getLabel())
                .eqIfPresent(DictDataDO::getDictType, reqVO.getDictType())
                .eqIfPresent(DictDataDO::getStatus, reqVO.getStatus())
                .orderByDesc(Arrays.asList(DictDataDO::getDictType, DictDataDO::getSort)));
    }

    default List<DictDataDO> selectListByStatusAndDictType(Integer status, String dictType) {
        return selectList(new LambdaQueryWrapperX<DictDataDO>()
                .eqIfPresent(DictDataDO::getStatus, status)
                .eqIfPresent(DictDataDO::getDictType, dictType));
    }

}
