package com.lzb.sqlSession;

import com.lzb.config.Configuration;
import com.lzb.io.XMLConfigBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class SqlSessionFactoryBuilder {
    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        XMLConfigBuilder xmlConfigerBuilder = new XMLConfigBuilder(configuration);
        Configuration configuration = xmlConfigerBuilder.parseConfiguration(inputStream);
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
