package com.titlark.utils;

import java.util.ResourceBundle;

/**
 * 读取 properties 常用工具类
 */
public class PropertiesUtil {

    private PropertiesUtil() {
    }

    /**
     * 默认读取 resource 文件夹下的配置文件，文件只输入文件名就行，如 jdbc.properties 输入 jdbc 就行，不需要输入后缀
     *
     * @param configName 配置文件文件名，不要后缀
     * @param fieldName  配置文件文件名，不要后缀
     * @return 配置文件文件名，不要后缀
     */
    public static String readString(String configName, String fieldName) {
        ResourceBundle bundle = ResourceBundle.getBundle(configName);
        // 过滤读取结果的空格
        return bundle.getString(fieldName).trim();
    }

}
