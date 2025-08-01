package com.veryclone.common.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    /**
     * 将文本中含有{}的括号中的字符串提出来。
     * @param input
     * @return
     */
    public static List<String> extractTextInBraces(String input) {
        List<String> result = new ArrayList<>();
        // 正则表达式匹配大括号内的内容，不包括大括号本身
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(input);
        
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        
        return result;
    }

    public static String removeBraces(String input) {
        // 使用正则表达式替换所有大括号（包括内容前后的大括号）
        return input.replaceAll("\\{|\\}", "");
    }

    public static void main(String[] args) {
        String text = "{想在TikTok上打开海外市场却无从下手？}\n" +
                "为广州做外贸出口的商家，包括工厂，档口，外贸公司等，做 TikTok 外贸引流，帮你搭建 TikTok ，教你如何在 TikTok 上做外贸。\n\n" +
                "{你可能想问：}\n" +
                "使用 TikTok 做外贸，有效果吗？真的能做吗？\n" +
                "{答案藏在数据里——}";
        
        List<String> extractedTexts = extractTextInBraces(text);
        
        System.out.println("提取到的内容:");
        for (String s : extractedTexts) {
            System.out.println(s);
        }

        String text2 = "{想在TikTok上打开海外市场却无从下手？}\n" +
                "为广州做外贸出口的商家，包括工厂，档口，外贸公司等，做 TikTok 外贸引流，帮你搭建 TikTok ，教你如何在 TikTok 上做外贸。\n\n" +
                "{你可能想问：}\n" +
                "使用 TikTok 做外贸，有效果吗？真的能做吗？\n" +
                "{答案藏在数据里——}";
        String result = removeBraces(text2);

        System.out.println("处理后的文本:");
        System.out.println(result);

    }
}