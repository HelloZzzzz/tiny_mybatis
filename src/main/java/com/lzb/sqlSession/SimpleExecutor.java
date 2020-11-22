package com.lzb.sqlSession;

import com.lzb.config.Configuration;
import com.lzb.config.MappedStatement;
import com.lzb.utils.GenericTokenParser;
import com.lzb.utils.ParameterMapping;
import com.lzb.utils.ParameterMappingTokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : LZB
 * @Date : 2020/10/12
 * @Description :
 */
public class SimpleExecutor implements Executor {
    private Connection connection = null;
    private static final Logger logger = LoggerFactory.getLogger(SimpleExecutor.class);

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {

        connection = configuration.getDataSource().getConnection();
        // select * from user where id = #{id} and username = #{username}
        String sql = mappedStatement.getSql();
        BoundSql boundsql = getBoundSql(sql);
        // select * from where id = ? and username = ?
        String finalSql = boundsql.getSqlText();

//        Class<?> paramterType = null;
        Class<?> paramterType = mappedStatement.getParameterType();

        PreparedStatement preparedStatement = connection.prepareStatement(finalSql);
        List<ParameterMapping> parameterMappingList = boundsql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String name = parameterMapping.getContent();
            Field declaredField = paramterType.getDeclaredField(name);
            declaredField.setAccessible(true);
            Object o = declaredField.get(param);
            preparedStatement.setObject(i + 1, o);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> resultType = mappedStatement.getResultType();
        ArrayList<E> results = new ArrayList<E>();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            E o = (E) resultType.newInstance();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            results.add(o);
        }
        return results;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {
        connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        BoundSql boundsql = getBoundSql(sql);
        // update `spring_jdbc`.`account` set money = ? where id = ?
        String finalSql = boundsql.getSqlText();
        logger.info("finalSql：{}", finalSql);
        Class<?> paramterType = mappedStatement.getParameterType();
        PreparedStatement preparedStatement = connection.prepareStatement(finalSql);
        List<ParameterMapping> parameterMappingList = boundsql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String name = parameterMapping.getContent();
            Field declaredField = paramterType.getDeclaredField(name);
            declaredField.setAccessible(true);
            Object o = declaredField.get(param);
            preparedStatement.setObject(i + 1, o);
        }
        return preparedStatement.executeUpdate();
    }


    @Override
    public void close() throws SQLException {
        connection.close();
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();

        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parse, parameterMappings);
        return boundSql;

    }
}