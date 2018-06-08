package com.liupeng.dao.sqlexec;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SqlExecutorMapper {

    public List<Map<String, Object>> sqlExecutorSelect(@Param(value = "sql") String sql);

    public int sqlExecutorInsert(@Param(value = "sql") String sql);

    public int sqlExecutorUpdate(@Param(value = "sql") String sql);
}
