package com.lzb.io;

import java.io.InputStream;

/**
 * @Author : LZB
 * @Date : 2020/10/11
 * @Description :
 */
public class Resources {
    public static InputStream getResourceAsStream(String path) {
        ClassLoader classLoader = Resources.class.getClassLoader();
        return classLoader.getResourceAsStream(path);
    }

}
