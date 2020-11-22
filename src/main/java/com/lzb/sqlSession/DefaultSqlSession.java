package com.lzb.sqlSession;

import com.lzb.config.Configuration;
import com.lzb.config.MappedStatement;
import com.lzb.enums.SqlCommandTypeEnum;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;

    }

    private Executor simpleExcutor = new SimpleExecutor();

    @Override
    public <E> List<E> selectList(String statementId, Object param) throws
            Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> query = simpleExcutor.query(configuration, mappedStatement, param);
        return query;
    }

    @Override
    public <T> T selectOne(String statementId, Object params) throws
            Exception {
        List<T> objects = selectList(statementId, params);
        if (null == objects || 0 == objects.size()) {
            return null;
        } else if (objects.size() == 1) {
            return objects.get(0);
        } else {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + objects.size());
        }
    }

    @Override
    public int insert(String statement) throws Exception {
        return this.update(statement);
    }

    @Override
    public int insert(String statement, Object parameter) throws Exception {
        return this.update(statement, parameter);
    }

    @Override
    public int update(String statementId) throws Exception {
        return this.update(statementId, null);
    }

    @Override
    public int update(String statementId, Object parameter) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExcutor.update(configuration, mappedStatement, parameter);
    }

    @Override
    public int delete(String statement) throws Exception {
        return this.update(statement);
    }

    @Override
    public int delete(String statement, Object parameter) throws Exception {
        return this.update(statement, parameter);
    }

    public void close() throws SQLException {
        simpleExcutor.close();
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK动态代理来为Dao接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, (proxy, method, args) -> {
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();

            String statementId = className + "." + methodName;
            MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
            SqlCommandTypeEnum sqlCommandTypeEnum = mappedStatement.getSqlCommandTypeEnum();
            Object param = null == args ? null : args[0];
            if (sqlCommandTypeEnum.equals(SqlCommandTypeEnum.SELECT)) {
                Class<?> returnType = method.getReturnType();
                if (returnType.getTypeName().equals(List.class.getName())) {
                    return selectList(statementId, param);
                } else {
                    return selectOne(statementId, param);
                }
            } else {
                return update(statementId, param);
            }

        });

        return (T) proxyInstance;
    }


}