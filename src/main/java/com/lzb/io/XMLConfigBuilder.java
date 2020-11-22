package com.lzb.io;

import com.lzb.config.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parseConfiguration(InputStream inputStream) throws
            DocumentException, PropertyVetoException, ClassNotFoundException {
        Document document = new SAXReader().read(inputStream);
        //<configuation>
        Element rootElement = document.getRootElement();
        List<Element> propertyElements = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element propertyElement : propertyElements) {
            String name = propertyElement.attributeValue("name");
            String value = propertyElement.attributeValue("value");
            properties.setProperty(name, value);
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        comboPooledDataSource.setDriverClass(properties.getProperty("jdbc.driver"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbc.url"));
        comboPooledDataSource.setUser(properties.getProperty("jdbc.username"));
        comboPooledDataSource.setPassword(properties.getProperty("jdbc.password"));

        configuration.setDataSource(comboPooledDataSource);

        List<Element> mapperElements = rootElement.selectNodes("//mapper");
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        for (Element mapperElement : mapperElements) {
            String mapperPath = mapperElement.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getResourceAsStream(mapperPath);
            xmlMapperBuilder.parse(resourceAsSteam);
        }
        return configuration;

    }
}