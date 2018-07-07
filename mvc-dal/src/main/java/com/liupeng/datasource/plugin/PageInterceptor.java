package com.liupeng.datasource.plugin;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * mybatis分页拦截器
 *
 * @author fengdao.lp
 * @date 2018/7/7
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {
    private Integer pageSize;
    private Integer currentPage;

    /**
     * 拦截以ByPage结尾的mapper中方法的调用
     */
    private String pageParam = "ByPage";

    /**
     * 过虑参数
     */
    private String filterParam = "";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        while (metaObject.hasGetter("h")) {
            Object o = metaObject.getValue("h");
            metaObject = SystemMetaObject.forObject(o);
        }

        while (metaObject.hasGetter("target")) {
            Object o = metaObject.getValue("target");
            metaObject = SystemMetaObject.forObject(o);
        }

        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        String mapId = mappedStatement.getId();
        // 拦截ByCount结尾的方法，计算总数
        if (mapId.matches(".+ByCount$")) {
            String sql = (String)metaObject.getValue("delegate.boundSql.sql");
            String filterSql = sql + " " + filterParam;
            metaObject.setValue("delegate.boundSql.sql", filterSql);
        }
        // 动态替换sql语句，这里才是真正需要执行的sql语句
        else if (mapId.matches(".+" + pageParam + "$")) {
            ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue("delegate.parameterHandler");
            Map<String, Object> paramObject = (Map<String, Object>)parameterHandler.getParameterObject();
            currentPage = (Integer)paramObject.get("currentPage");
            pageSize = (Integer)paramObject.get("pageSize");

            String sql = ((String)metaObject.getValue("delegate.boundSql.sql")).trim();
            String limitSql = sql + " " + filterParam + " limit " + (currentPage - 1) * pageSize + "," + pageSize;
            metaObject.setValue("delegate.boundSql.sql", limitSql);
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
        this.pageSize = Integer.valueOf(properties.getProperty("limit", "10"));
    }
}
