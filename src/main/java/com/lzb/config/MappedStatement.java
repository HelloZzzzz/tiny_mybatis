package com.lzb.config;

import com.lzb.enums.SqlCommandTypeEnum;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class MappedStatement {

    private String id;
    private Class<?> parameterType;
    private Class<?> resultType;
    private String sql;
    private SqlCommandTypeEnum sqlCommandTypeEnum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlCommandTypeEnum getSqlCommandTypeEnum() {
        return sqlCommandTypeEnum;
    }

    public void setSqlCommandTypeEnum(SqlCommandTypeEnum sqlCommandTypeEnum) {
        this.sqlCommandTypeEnum = sqlCommandTypeEnum;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "id='" + id + '\'' +
                ", parameterType=" + parameterType +
                ", resultType=" + resultType +
                ", sql='" + sql + '\'' +
                ", sqlCommandTypeEnum=" + sqlCommandTypeEnum +
                '}';
    }
}
