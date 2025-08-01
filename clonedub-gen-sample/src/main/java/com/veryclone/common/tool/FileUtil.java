package com.veryclone.common.tool;

public class FileUtil {
    // 获取文件扩展名
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}