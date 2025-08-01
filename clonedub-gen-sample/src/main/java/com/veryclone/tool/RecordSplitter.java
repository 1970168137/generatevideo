package com.veryclone.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 把list按一定数量分包
 */
public class RecordSplitter {

    /**
     *
     * @param list
     * @param batchSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitIntoBatches(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        int i = 0;

        while (i < list.size()) {
            // 计算当前批次的结束索引
            int end = Math.min(i + batchSize, list.size());
            // 获取当前批次的子列表
            List<T> batch = list.subList(i, end);
            // 将子列表添加到批次列表中
            batches.add(batch);
            // 更新下一批的起始索引
            i = end;
        }

        return batches;
    }

    // 主方法，用于测试
    public static void main(String[] args) {
        // 假设这是您的记录列表
        List<String> records = new ArrayList<>();
        // 填充记录列表（仅用于示例）
        for (int i = 0; i < 20; i++) {
            records.add("Record " + (i + 1));
        }

        // 调用分割方法，每个批次包含6条记录
        List<List<String>> batches = splitIntoBatches(records, 6);

        // 打印结果
        for (List<String> batch : batches) {
            System.out.println("Batch: " + batch);
        }
    }
}