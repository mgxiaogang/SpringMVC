package com.liupeng.sqlexec.vo;

/**
 * @author 刘鹏
 */
public class SqlRequestVo {
    /**
     * 用户名
     */
    private int uid;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * sql语句
     */
    private String sqlStatement;

    /**
     * get uid
     *
     * @return Returns the uid.<br>
     */
    public int getUid() {
        return uid;
    }

    /**
     * set uid
     *
     * @param uid The uid to set. <br>
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * get password
     *
     * @return Returns the password.<br>
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password
     *
     * @param password The password to set. <br>
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get sqlStatement
     *
     * @return Returns the sqlStatement.<br>
     */
    public String getSqlStatement() {
        return sqlStatement;
    }

    /**
     * set sqlStatement
     *
     * @param sqlStatement The sqlStatement to set. <br>
     */
    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    /**
     * get dbName
     *
     * @return Returns the dbName.<br>
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * set dbName
     *
     * @param dbName The dbName to set. <br>
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "SqlRequestVo{" +
            "uid=" + uid +
            ", password='" + password + '\'' +
            ", dbName='" + dbName + '\'' +
            ", sqlStatement='" + sqlStatement + '\'' +
            '}';
    }
}
