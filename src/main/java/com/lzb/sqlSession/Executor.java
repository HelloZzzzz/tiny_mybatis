package com.lzb.sqlSession;

import com.lzb.config.Configuration;
import com.lzb.config.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author : LZB
 * @Date : 2020/10/12
 * @Description :
 */
public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

    int update(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

    void close() throws SQLException;
}