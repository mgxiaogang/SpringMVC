package com.liupeng.datasource.plugin.mePlugin;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 文档：https://blog.csdn.net/chenbaige/article/details/72084481
 *
 * @author fengdao.lp
 * @date 2018/7/7
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}))
public class PageInterceptorMe implements Interceptor {
    /**
     * 默认页码
     */
    private Integer defaultPage;
    /**
     * 默认每页显示条数
     */
    private Integer defaultPageSize;
    /**
     * 是否启用分页功能
     */
    private boolean defaultUseFlag;
    /**
     * 检测当前页码的合法性（大于最大页码或小于最小页码都不合法）
     */
    private boolean defaultCheckFlag;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取StatementHandler，默认是RoutingStatementHandler
        StatementHandler statementHandler = PageUtil.getActuralHandlerObject(invocation);
        // 获取statementHandler包装类
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 获取SQL语句
        String sql = statementHandler.getBoundSql().getSql();
        if (!PageUtil.checkIsSelectFalg(sql)) {
            return invocation.proceed();
        }
        BoundSql boundSql = statementHandler.getBoundSql();
        Object paramObject = boundSql.getParameterObject();
        PageParam pageParam = PageUtil.getPageParam(paramObject);
        if (pageParam == null) {
            return invocation.proceed();
        }

        Integer pageNum = pageParam.getDefaultPage() == null ? defaultPage : pageParam.getDefaultPage();
        Integer pageSize = pageParam.getDefaultPageSize() == null ? defaultPageSize : pageParam.getDefaultPageSize();
        Boolean useFlag = pageParam.isDefaultUseFlag() == null ? defaultUseFlag : pageParam.isDefaultUseFlag();
        Boolean checkFlag = pageParam.isDefaultCheckFlag() == null ? defaultCheckFlag : pageParam.isDefaultCheckFlag();
        // 不启用分页功能则直接返回
        if (!useFlag) {
            return invocation.proceed();
        }

        // 重写SQL，执行获取总数
        int total = PageUtil.getTotle(invocation, metaObject, boundSql);
        // 将动态获取到的分页参数回填到pageParam中
        PageUtil.setTotltToParam(pageParam, total, pageSize);
        // 检查当前页码的有效性
        PageUtil.checkPage(checkFlag, pageNum, pageParam.getTotlePage());
        // 重写SQL
        return PageUtil.updateSql2Limit(invocation, metaObject, boundSql, pageNum, pageSize);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String strDefaultPage = properties.getProperty("default.page");
        String strDefaultPageSize = properties.getProperty("default.pageSize");
        String strDefaultUseFlag = properties.getProperty("default.useFlag");
        String strDefaultCheckFlag = properties.getProperty("default.checkFlag");
        defaultPage = Integer.valueOf(strDefaultPage);
        defaultPageSize = Integer.valueOf(strDefaultPageSize);
        defaultUseFlag = Boolean.valueOf(strDefaultUseFlag);
        defaultCheckFlag = Boolean.valueOf(strDefaultCheckFlag);
    }
}
