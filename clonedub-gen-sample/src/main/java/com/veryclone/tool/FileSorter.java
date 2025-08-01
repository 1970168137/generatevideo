package com.veryclone.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSorter {

    /**
     * 根据文件的最后修改时间对文件列表进行升序排序。
     *
     * @param fileList 要排序的文件列表
     */
    public static void sortByModificationTime(List<File> fileList) {
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                // 比较两个文件的最后修改时间
                long time1 = file1.lastModified();
                long time2 = file2.lastModified();
                // 如果时间1小于时间2，返回负数，实现升序排序
                return Long.compare(time1, time2);
            }
        });
    }

    /**
     * 根据文件名称来进行升序排序
     *
     * @param fileList 要排序的文件列表
     */
    public static void sortByFileName(List<File> fileList) {
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                // 比较两个文件的最后修改时间
                long time1 = extractNumbers(file1.getName());
                long time2 = extractNumbers(file2.getName());
                // 如果时间1小于时间2，返回负数，实现升序排序
                return Long.compare(time1, time2);
            }
        });
    }

    public static long extractNumbers(String input) {
        // 正则表达式匹配所有数字
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        // 用于构建结果字符串的StringBuilder
        StringBuilder result = new StringBuilder();

        // 遍历所有匹配的数字
        while (matcher.find()) {
            // 将匹配到的数字追加到StringBuilder中
            result.append(matcher.group());
        }

        // 将StringBuilder转换为字符串并返回
        return Long.parseLong(result.toString());
    }

    // 主方法，用于测试
    public static void main(String[] args) {
        // 假设这是您的文件列表（仅用于示例）
        List<File> fileList = new ArrayList<>();
        // 填充文件列表（在实际应用中，您可能会从某个目录读取文件）
        // ...

        // 调用排序方法
        sortByModificationTime(fileList);

        // 打印排序后的文件列表
        for (File file : fileList) {
            System.out.println(file.getAbsolutePath() + " - " + file.lastModified());
        }
    }
}