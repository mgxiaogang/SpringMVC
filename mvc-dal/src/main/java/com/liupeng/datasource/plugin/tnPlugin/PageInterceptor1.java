package com.liupeng.datasource.plugin.tnPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.liupeng.util.ReflectUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

/**
 * mybatis分页拦截器
 *
 * @author fengdao.lp
 * @date 2018/7/7
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor1 implements Interceptor {
    private String dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object statementHandler = invocation.getTarget();
        StatementHandler delegate = null;
        try {
            if (statementHandler instanceof RoutingStatementHandler) {
                delegate = (BaseStatementHandler)ReflectUtil.getFieldValueByFieldName(statementHandler, "delegate");
            } else {
                delegate = (BaseStatementHandler)statementHandler;
            }
        } catch (Exception e) {
            return invocation.proceed();
        }

        RowBounds rowBounds = (RowBounds)ReflectUtil.getFieldValueByFieldName(delegate, "rowBounds");
        if (rowBounds != null && !Pagination.noRow(rowBounds)) {
            Pagination pagination = null;
            if (rowBounds instanceof Pagination) {
                pagination = (Pagination)rowBounds;
            } else {
                pagination = Pagination.getPagination(rowBounds);
            }
            BoundSql boundSql = delegate.getBoundSql();
            String sql = boundSql.getSql();

            // 如果需要统计开关
            if (pagination.isNeedCount()) {
                Configuration configuration = (Configuration)ReflectUtil.getFieldValueByFieldName(delegate,
                    "configuration");
                MappedStatement mappedStatement = (MappedStatement)ReflectUtil.getFieldValueByFieldName(delegate,
                    "mappedStatement");
                String countSqlId = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf('.') + 1);
                countSqlId = "count" + countSqlId.substring(0, 1).toUpperCase() + countSqlId.substring(1);
                String countSql = null;
                //取count sql
                if (configuration.hasStatement(countSqlId, false)) {
                    ParameterHandler parameterHandler = (ParameterHandler)ReflectUtil.getFieldValueByFieldName(delegate,
                        "parameterHandler");
                    countSql = configuration.getMappedStatement(countSqlId).getSqlSource().getBoundSql(
                        parameterHandler.getParameterObject()).getSql();
                } else {
                    countSql = SqlDialect.getCountSql(sql, dialect);
                }
                PreparedStatement countPs = null;
                ResultSet rs = null;
                Connection connection = (Connection)invocation.getArgs()[0];
                try {
                    countPs = connection.prepareStatement(countSql);
                    delegate.getParameterHandler().setParameters(countPs);
                    rs = countPs.executeQuery();
                    if (rs.next()) {
                        pagination.setCount(rs.getInt(1));
                    }
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (countPs != null) {
                        countPs.close();
                    }
                }
            }
            ReflectUtil.setValueByFieldName(boundSql, "sql",
                SqlDialect.getPaginationSql(sql, pagination.getStartIndex(), pagination.getLimit(), dialect));
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 默认参数
        this.dialect = properties.getProperty("dbType", "mysql");
    }
}
