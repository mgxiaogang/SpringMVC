package com.liupeng.datasource.plugin.mePlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

/**
 * @author fengdao.lp
 * @date 2018/7/7
 */
public class PageUtil {
    /**
     * 从代理对象中分离出真实statementHandler对象,非代理对象
     */
    public static StatementHandler getActuralHandlerObject(Invocation invocation) {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        Object object = null;
        // 分离代理对象链，目标可能被多个拦截器拦截，分离出最原始的目标类
        while (metaStatementHandler.hasGetter("h")) {
            object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        if (object == null) {
            return statementHandler;
        }
        return (StatementHandler)object;
    }

    /**
     * 判断是否是select语句
     */
    public static boolean checkIsSelectFalg(String sql) {
        String trimSql = sql.trim();
        int index = trimSql.toLowerCase().indexOf("select");
        return index == 0;
    }

    /**
     * 获取分页参数，
     * <pre>
     *     参数传入方式：
     *     1.map
     *     2.@param注解
     *     3.请求POJO继承自PageParam，将PageParam中的分页数据放进去
     * </pre>
     */
    public static PageParam getPageParam(Object paramerObject) {
        if (paramerObject == null) {
            return null;
        }
        PageParam pageParam = null;
        // 首先处理传递进来的是map对象和通过注解方式传值的情况，从中提取出PageParam,循环获取map中的键值对，取出PageParam对象
        if (paramerObject instanceof Map) {
            Map<String, Object> params = (Map<String, Object>)paramerObject;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof PageParam) {
                    return (PageParam)entry.getValue();
                }
            }
        }
        // 继承方式 pojo继承自PageParam 只取出我们希望得到的分页参数
        else if (paramerObject instanceof PageParam) {
            pageParam = (PageParam)paramerObject;

        }
        return pageParam;
    }

    public static void setTotltToParam(PageParam param, int totle, int pageSize) {
        param.setTotle(totle);
        param.setTotlePage(totle % pageSize == 0 ? totle / pageSize : (totle / pageSize) + 1);
    }

    public static void checkPage(boolean checkFlag, Integer pageNumber, Integer pageTotle) throws Exception {
        if (checkFlag) {
            if (pageNumber > pageTotle) {
                throw new Exception("查询失败，查询页码" + pageNumber + "大于总页数" + pageTotle);
            }
        }
    }

    /**
     * 改写SQL，获取总数
     */
    public static int getTotle(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql) {
        // 获取mapper文件中当前查询语句的配置信息
        MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");
        // 获取所有配置Configuration
        org.apache.ibatis.session.Configuration configuration = mappedStatement.getConfiguration();
        // 获取当前查询语句的sql
        String sql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
        // 将sql改写成统计记录数的sql语句,这里是mysql的改写语句,将第一次查询结果作为第二次查询的表
        String countSql = "select count(*) as totle from (" + sql + ") $_paging";
        // 获取connection连接对象，用于执行countsql语句
        Connection conn = (Connection)invocation.getArgs()[0];
        PreparedStatement ps = null;
        int totle = 0;
        try {
            // 预编译统计总记录数的sql
            ps = conn.prepareStatement(countSql);
            //构建统计总记录数的BoundSql
            BoundSql countBoundSql = new BoundSql(configuration, countSql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());
            //构建ParameterHandler，用于设置统计sql的参数
            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,
                boundSql.getParameterObject(), countBoundSql);
            //设置总数sql的参数
            parameterHandler.setParameters(ps);
            //执行查询语句
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // 与countSql中设置的别名对应
                totle = rs.getInt("totle");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return totle;
    }

    /**
     * 修改SQL
     */
    public static Object updateSql2Limit(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql, int page,
                                   int pageSize) throws Exception {
        String sql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
        //构建新的分页sql语句
        String limitSql = "select * from (" + sql + ") $_paging_table limit ?,?";
        //修改当前要执行的sql语句
        metaStatementHandler.setValue("delegate.boundSql.sql", limitSql);
        //相当于调用prepare方法，预编译sql并且加入参数，但是少了分页的两个参数，它返回一个PreparedStatement对象
        PreparedStatement ps = (PreparedStatement)invocation.proceed();
        //获取sql总的参数总数
        int count = ps.getParameterMetaData().getParameterCount();
        //设置与分页相关的两个参数
        ps.setInt(count - 1, (page - 1) * pageSize);
        ps.setInt(count, pageSize);
        return ps;
    }
}
