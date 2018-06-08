package com.liupeng.sqlexec.service.sqlexe;

import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.vo.SqlRequestVo;

/**
 * Sql执行器接口
 */
public interface ISqlExecutorService {
    ResponseVo exeSql(HttpServletRequest httpRequest, SqlRequestVo sqlRequest);
}
