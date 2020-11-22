package com.lzb.io;

import com.lzb.config.Configuration;
import com.lzb.config.MappedStatement;
import com.lzb.enums.SqlCommandTypeEnum;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException,
            ClassNotFoundException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        /*
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            String name = element.getName();// 属性名称
            String id = element.attributeValue("id");
            String value = element.getStringValue();// 属性的值
            System.out.println("属性名称：" + name + "---->属性值：" + value + "---->id"+id);
        }*/


        String namespace = rootElement.attributeValue("namespace");
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            String name = element.getName();
            String id = element.attributeValue("id");
            String paramterType = element.attributeValue("paramterType");
            String resultType = element.attributeValue("resultType");
            Class<?> paramterTypeClass = getClassType(paramterType);
            Class<?> resultTypeClass = getClassType(resultType);
            String key = namespace + "." + id;
            String textTrim = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setSqlCommandTypeEnum(SqlCommandTypeEnum.valueOf(name.toUpperCase()));
            mappedStatement.setId(id);
            mappedStatement.setParameterType(paramterTypeClass);
//            mappedStatement.setParamterType(paramterTypeClass);
            mappedStatement.setResultType(resultTypeClass);

//            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setSql(textTrim);
            configuration.getMappedStatementMap().put(key, mappedStatement);

        }
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        try {
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        } catch (NullPointerException e) {
            return null;
        }

    }
}