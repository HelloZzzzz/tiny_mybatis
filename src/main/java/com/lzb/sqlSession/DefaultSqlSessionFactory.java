package com.lzb.sqlSession;

import com.lzb.config.Configuration;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}