package com.veryclone.ai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.veryclone.common.ClonedubGenConfig;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 接入的是火山引擎部署的deepseek R1 API
 * 网站是https://console.volcengine.com/，注册-》登录-》文本模型-》接入API，就会获得apiKEY，和调用地址
 */
public class HuoshanAPIClient {
    private static final String apiKey = ClonedubGenConfig.getValue("deepseeskApiKey"); //"af19c4c7-8280-4159-bfe7-39a2ce178a76"; // 替换为你的实际 API key

    private static final String API_URL = ClonedubGenConfig.getValue("deepseeskApiUrl");;
    private final OkHttpClient client;

    public HuoshanAPIClient() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public String sendRequest(String apiKey, String message) throws Exception {
        // 1. 构建请求体 - 使用正确的模型名称
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-r1-250120");
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
        HuoshanAPIClient client = new HuoshanAPIClient();
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
        //返回的结果，有回车符，先去掉
        return assistantReply.replaceFirst("^(\\n){2}", "");
    }

    public static void main(String[] args) {
        HuoshanAPIClient client = new HuoshanAPIClient();

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