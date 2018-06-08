package com.liupeng.sqlexec.service.chain;

import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.vo.SqlRequestVo;

/**
 * sql执行链抽象类
 */
public abstract class AbsSqlExecutorChainServiceImpl implements ISqlExecutorChain {

    @Override
    public ResponseVo doSqlHandle(HttpServletRequest httpRequest, SqlRequestVo sqlRequest) {
        ResponseVo vo = this.realDoSqlHandle(httpRequest, sqlRequest);
        // 验证成功就转给一下继任者
        if (vo != null && vo.isSuccess()) {
            ISqlExecutorChain nextCheckChain = getNextSqlChain();
            if (null != nextCheckChain) {
                vo = nextCheckChain.doSqlHandle(httpRequest, sqlRequest);
            }
        }
        return vo;
    }

    /**
     * 定义链中每个处理者具体的处理方式
     */
    protected abstract ResponseVo realDoSqlHandle(HttpServletRequest httpRequest, SqlRequestVo sqlRequest);

    /**
     * 获取后继
     */
    public abstract ISqlExecutorChain getNextSqlChain();

}
