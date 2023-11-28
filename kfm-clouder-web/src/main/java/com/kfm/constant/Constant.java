package com.kfm.constant;

import com.kfm.utils.PropertiesUtils;

import java.io.IOException;

public class Constant {
    public static final int RETURN_VALUE_ONE = -1;
    public static String UPLOAD_PATH = null;

    static {
        try {
            UPLOAD_PATH = PropertiesUtils.getProperties().getProperty("filePath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String LOGIN_USER_KEY = "user";
}
