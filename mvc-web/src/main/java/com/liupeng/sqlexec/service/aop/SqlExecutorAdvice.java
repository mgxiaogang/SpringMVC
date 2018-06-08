package com.liupeng.sqlexec.service.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.liupeng.annotationdriven.response.ResponseVo;
import com.liupeng.sqlexec.service.chain.ISqlExecutorChain;
import com.liupeng.sqlexec.util.SqlUtil;
import com.liupeng.sqlexec.vo.SqlRequestVo;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sql执行器Advice
 */
public class SqlExecutorAdvice implements MethodInterceptor {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SqlExecutorAdvice.class);

    @Resource(name = "nullPointerChainServiceImpl")
    private ISqlExecutorChain checkChain;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;
        ResponseVo vo = new ResponseVo();
        StringBuilder msg = new StringBuilder();
        HttpServletRequest httpRequest;
        SqlRequestVo sqlRequest;
        try {
            Object[] args = invocation.getArguments();
            // 提取参数
            httpRequest = SqlUtil.getHttpServletRequestArg(args);
            sqlRequest = SqlUtil.getSqlRequestVoArg(args);
            // 链式执行
            vo = checkChain.doSqlHandle(httpRequest, sqlRequest);
            if (vo == null || !vo.isSuccess()) {
                msg.append(vo == null ? "校验失败" : vo.getMsg());
                logger.error(msg.toString());
                return vo;
            }
            result = invocation.proceed();
            logger.info(invocation.getMethod().getName() + " ends,result: {}", result);
            return result;
        } catch (Exception e) {
            msg.append("SqlExecutorAdvice Exception: ").append(e);
            logger.error(msg.toString());
            vo.setSuccess(false);
            return vo;
        } finally {
            // 必须做记录发邮件
        }
    }
}