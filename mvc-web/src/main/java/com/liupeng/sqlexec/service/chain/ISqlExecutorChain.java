package com.liupeng.sqlexec.service.chain;

import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.vo.SqlRequestVo;

/**
 * sql执行器链
 */
public interface ISqlExecutorChain {
    public ResponseVo doSqlHandle(HttpServletRequest httpRequest, SqlRequestVo sqlRequest);
}