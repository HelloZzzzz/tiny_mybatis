package com.lzb.sqlSession;


import com.lzb.bean.User;
import com.lzb.config.Configuration;
import com.lzb.config.MappedStatement;
import com.lzb.io.Resources;
import com.lzb.io.XMLConfigBuilder;
import com.lzb.io.XMLConfigBuilderTest;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;


/**
 * @Author : LZB
 * @Date : 2020/10/15
 * @Description :
 */

public class SimpleExecutorTest {


    @Test
    public void query() {
    }

    @Test
    public void update() throws Exception {
        Executor executor = new SimpleExecutor();
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        Configuration configuration = xmlConfigBuilder.parseConfiguration(resourceAsStream);
        MappedStatement mappedStatement = new MappedStatement();
        mappedStatement.setSql("update `tiny_mybatis`.`user` set password = #{password} where id = #{id}");
        mappedStatement.setParameterType(User.class);
        User user = new User();
        user.setUsername("liaowei");
        user.setPassword("1111111");
        user.setId(1);
        System.out.println("executor.update(configuration, mappedStatement, param) = " + executor.update(configuration, mappedStatement, user));


    }
}
