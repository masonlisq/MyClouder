package com.kfm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtils {
    public static Properties getProperties() throws IOException {
        InputStream resourceAsStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("server.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        return properties;
    }
}
