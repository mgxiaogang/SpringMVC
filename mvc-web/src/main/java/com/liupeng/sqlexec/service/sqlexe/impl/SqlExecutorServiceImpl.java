package com.liupeng.sqlexec.service.sqlexe.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.service.chain.ISqlExecutorChain;
import com.liupeng.sqlexec.service.sqlexe.ISqlExecutorService;
import com.liupeng.sqlexec.util.SqlUtil;
import com.liupeng.sqlexec.vo.SqlRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * sql执行器实现
 */
@Service
public class SqlExecutorServiceImpl implements ISqlExecutorService {
    /**
     * 日志打印
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlExecutorServiceImpl.class);

    @Resource(name = "dataSourceCheckChainServiceImpl")
    private ISqlExecutorChain sqlChain;

    /**
     * 执行sql
     */
    @Override
    public ResponseVo exeSql(HttpServletRequest httpRequest, SqlRequestVo sqlRequest) {
        LOGGER.info("用户: " + sqlRequest.getUid() + " 开始执行sql");
        ResponseVo vo = sqlChain.doSqlHandle(httpRequest, sqlRequest);
        if (vo == null) {
            vo = new ResponseVo();
            SqlUtil.setResposeVo(vo, false, 1, null);
        }
        LOGGER.info("用户: " + sqlRequest.getUid() + " sql执行完毕");
        return vo;
    }

}
