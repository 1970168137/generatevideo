package com.veryclone.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFilter {

    public static List<File> filterFiles(List<File> fileList) {
        List<File> filteredList = new ArrayList<>();

        // 获取系统时间并减去10分钟
        long currentTime = System.currentTimeMillis();
        long tenMinutesAgo = currentTime - 10 * 60 * 1000; // 10分钟的毫秒数

        // 遍历文件列表
        for (File file : fileList) {
            // 检查文件的最后修改时间
            long fileLastModified = file.lastModified();

            // 检查文件是否在10分钟之前修改的
            if (fileLastModified > tenMinutesAgo) {
                continue; // 跳过这个文件
            }

            // 将文件添加到过滤后的列表中
            filteredList.add(file);
        }


        return filteredList;
    }

    // 主方法，用于测试
    public static void main(String[] args) {
        // 假设这是您的文件列表
        List<File> fileList = new ArrayList<>();
        // 填充文件列表（仅用于示例）
        // 在实际应用中，您可能会从某个目录读取文件

        // 调用过滤方法
        List<File> filteredFileList = filterFiles(fileList);

        // 打印结果
        for (File file : filteredFileList) {
            System.out.println(file.getAbsolutePath());
        }
    }
}