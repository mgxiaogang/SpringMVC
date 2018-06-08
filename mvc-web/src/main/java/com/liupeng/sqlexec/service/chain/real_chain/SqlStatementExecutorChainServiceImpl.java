package com.liupeng.sqlexec.service.chain.real_chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.dao.sqlexec.SqlExecutorMapper;
import com.liupeng.sqlexec.enums.DMLTypeEnum;
import com.liupeng.sqlexec.service.chain.AbsSqlExecutorChainServiceImpl;
import com.liupeng.sqlexec.service.chain.ISqlExecutorChain;
import com.liupeng.sqlexec.util.SqlUtil;
import com.liupeng.sqlexec.vo.SqlRequestVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * sql语句的校验和执行
 */
@Service
public class SqlStatementExecutorChainServiceImpl extends AbsSqlExecutorChainServiceImpl {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SqlStatementExecutorChainServiceImpl.class);

    /**
     * sql的文本的最大值
     */
    private final int MAX_SQL_LEN = 1024 * 1000;

    /**
     * 终止执行链
     */
    private ISqlExecutorChain nextsqlChain = null;

    @Resource
    private SqlExecutorMapper sqlExecutorMapper;

    @Override
    protected ResponseVo realDoSqlHandle(HttpServletRequest httpRequest, SqlRequestVo sqlRequest) {
        long beginTime = System.currentTimeMillis();
        ResponseVo vo = new ResponseVo();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        StringBuilder sb = new StringBuilder();
        String sqlStatement = sqlRequest.getSqlStatement();
        if (sqlStatement.length() > MAX_SQL_LEN) {
            sb.append("sql语句不得大小超过：").append(MAX_SQL_LEN).append(" 个字符.");
            SqlUtil.setResposeVo(vo, false, 1, sb.toString());
            logger.error(sb.toString());
            return vo;
        }
        String[] sqlList = sqlStatement.split(";");
        List<Map<String, Object>> queryList = null;
        List<Map<String, Object>> reultListMap = new ArrayList<Map<String, Object>>();
        resultMap.put("sql", sqlList);
        for (String sql : sqlList) {
            DMLTypeEnum dmlTypeEnum = SqlUtil.checkDMLType(sql);
            if (DMLTypeEnum.SELECT == dmlTypeEnum) {
                queryList = sqlExecutorMapper.sqlExecutorSelect(sql);
                if (CollectionUtils.isNotEmpty(queryList)) {
                    reultListMap.addAll(queryList);
                }
            } else if (DMLTypeEnum.INSERT == dmlTypeEnum) {
                sqlExecutorMapper.sqlExecutorInsert(sql);
            } else if (DMLTypeEnum.UPDATE == dmlTypeEnum) {
                sqlExecutorMapper.sqlExecutorUpdate(sql);
            } else {
                vo.setSuccess(false);
                vo.setMsg("仅支持增、改、查！");
            }
        }
        resultMap.put("resultData", reultListMap);
        vo.setData(resultMap);
        vo.setMsg("执行完毕,耗时：" + (System.currentTimeMillis() - beginTime) + " ms");
        return vo;
    }

    @Override
    public ISqlExecutorChain getNextSqlChain() {
        return nextsqlChain;
    }
}
