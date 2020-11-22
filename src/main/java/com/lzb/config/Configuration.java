package com.lzb.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class Configuration {
    /*数据源
    private String driver;
    private String url;
    private String username;
    private String password;
    */
    private DataSource dataSource;
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
