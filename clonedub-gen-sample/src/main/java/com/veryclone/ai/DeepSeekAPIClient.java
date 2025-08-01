package com.veryclone.ai;

import okhttp3.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 调研deepseek模型的接口
 * 可以设置使用那额模型，设定方法：
 *      修改为正确的模型名称，根据以上说明，model有两个值 deepseek-chat 和 deepseek-reasoner
 *      requestBody.put("model", "deepseek-chat");
 */
public class DeepSeekAPIClient {
    private static final String apiKey = "sk-d3700a7b99f34a59a0c97f007f70703e"; // 替换为你的实际 API key

    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private final OkHttpClient client;

    public DeepSeekAPIClient() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public String sendRequest(String apiKey, String message) throws Exception {
        // 1. 构建请求体 - 使用正确的模型名称
        Map<String, Object> requestBody = new HashMap<>();

        /**
         * 官方 https://api-docs.deepseek.com/zh-cn/#%E8%B0%83%E7%94%A8%E5%AF%B9%E8%AF%9D-api 这里用具体版本调用说明，摘取部分内容如下：
         *
         * 出于与 OpenAI 兼容考虑，您也可以将 base_url 设置为 https://api.deepseek.com/v1 来使用，但注意，此处 v1 与模型版本无关。
         *
         * * deepseek-chat 模型已全面升级为 DeepSeek-V3，接口不变。 通过指定 model='deepseek-chat' 即可调用 DeepSeek-V3。
         *
         * * deepseek-reasoner 是 DeepSeek 最新推出的推理模型 DeepSeek-R1。通过指定 model='deepseek-reasoner'，即可调用 DeepSeek-R1。
         */
        requestBody.put("model", "deepseek-chat"); // 修改为正确的模型名称，根据以上说明，model有两个值 deepseek-chat 和 deepseek-reasoner

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> messageObj = new HashMap<>();
        messageObj.put("role", "user");
        messageObj.put("content", message);
        messages.add(messageObj);

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 2000);

        // 2. 将请求体转换为 JSON 字符串
        String jsonBody = JSON.toJSONString(requestBody);

        // 3. 创建请求体
        RequestBody body = RequestBody.create(
            jsonBody,
            MediaType.parse("application/json; charset=utf-8")
        );

        // 4. 构建请求
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        // 5. 发送请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API 请求失败，响应码: " + response.code() +
                                    ", 错误信息: " + response.body().string());
            }
            return response.body().string();
        }
    }

    /**
     * 提问获得答案
     * @param question
     * @return
     */
    public static String getAnswer(String question){
        DeepSeekAPIClient client = new DeepSeekAPIClient();
        String assistantReply = "";
        try {
            String response = client.sendRequest(apiKey, question);
            System.out.println("API 响应: " + response);

            // 解析响应
            JSONObject jsonResponse = JSON.parseObject(response);
            assistantReply = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            System.out.println("助手回复: " + assistantReply);
        } catch (Exception e) {
            System.out.println("方法 getAnswer(String question) 报错：" + e.toString());
            e.printStackTrace();
        }

        return assistantReply;
    }

    public static void main(String[] args) {
        DeepSeekAPIClient client = new DeepSeekAPIClient();

        try {
            String response = client.sendRequest(apiKey, "你好，DeepSeek!");
            System.out.println("API 响应: " + response);

            // 解析响应
            JSONObject jsonResponse = JSON.parseObject(response);
            String assistantReply = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            System.out.println("助手回复: " + assistantReply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}