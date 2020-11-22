package com.lzb.io;

import com.lzb.config.Configuration;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;


/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class XMLConfigBuilderTest {


    @Test
    public void parseConfiguration() throws DocumentException, PropertyVetoException, ClassNotFoundException {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        Configuration configuration = xmlConfigBuilder.parseConfiguration(resourceAsStream);
        System.out.println("configuration.getMappedStatementMap() = " + configuration.getMappedStatementMap());
    }
}
