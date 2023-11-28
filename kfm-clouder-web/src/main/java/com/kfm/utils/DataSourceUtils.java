package com.kfm.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DataSourceUtils {
    static Connection conn;
    public static Connection getConnection() throws Exception {
        if (conn!=null){
            return conn;
        }
        InputStream resourceAsStream = DataSourceUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        conn = dataSource.getConnection();
        return conn;
    }
}
