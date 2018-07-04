package com.liupeng.datasource.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.fastjson.JSON;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * mybatis JSON类型(通用类型)处理器
 * 案例
 * <pre>
 * 结果集：
 * <resultMap id="BaseResultMap" type="com.alibaba.wukong.dal.dataobject.brand.BrandDO">
 *      <id column="id" property="id"/>
 *      <result column="brand_behave_info" property="brandBehaveInfo" typeHandler="xx.JSONTypeHandler"/>
 * </resultMap>
 *
 * 新增和更新：
 * <if test="brandBehaveInfo != null">
 *      #{brandBehaveInfo,typeHandler=xx.JSONTypeHandler},
 * </if>
 * <pre/>
 */
public class JSONTypeHandler<T> extends BaseTypeHandler<T> {

    private Class<T> clazz;

    public JSONTypeHandler() {
    }

    public JSONTypeHandler(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (value != null) {
            return JSON.parseObject(value, clazz);
        }
        return null;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (value != null) {
            return JSON.parseObject(value, clazz);
        }
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (value != null) {
            return JSON.parseObject(value, clazz);
        }
        return null;
    }
}
