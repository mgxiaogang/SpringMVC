package com.liupeng.datasource.plugin;

public class SqlDialect {

    public static String getCountSql(String sql, String dbType) {
        return "SELECT COUNT(*) FROM (" + sql + ") rows";
    }

    public static String getPaginationSql(String sql, int offset, int size, String dbType) throws Exception {
        StringBuilder pSql = new StringBuilder();
        if ("mysql".equals(dbType)) {
            pSql.append(sql).append(" limit ").append(offset).append(",").append(size);
        } else {
            throw new Exception("不支持的数据库类型[" + dbType + "]，无法生成分页查询语句");
        }
        return pSql.toString();
    }
}
