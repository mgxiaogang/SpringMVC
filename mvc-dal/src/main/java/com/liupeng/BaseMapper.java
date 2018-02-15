package com.liupeng;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.condition.UpdateByConditionSelectiveMapper;

/**
 * @author fengdao.lp
 * @date 2018/2/14
 */
public interface BaseMapper<T> extends
    Mapper<T>,
    MySqlMapper<T>,
    BaseSelectMapper<T>,
    BaseInsertMapper<T>,
    BaseUpdateMapper<T>,
    UpdateByConditionSelectiveMapper<T> {
}
