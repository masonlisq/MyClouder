package com.kfm.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtils {
    /**
     * 获取Properties对象的方法
     * @return properties对象
     */
    public static Properties getProperties(){
        Properties properties = null;
        try {
            InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("client.properties");
            properties = new Properties();
            properties.load(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }
}
