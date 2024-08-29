package cn.heying.plus;

import cn.heying.plus.sdk.types.utils.WXAccessTokenUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ApiTest {
    @Test
    public void test() {
        System.out.println("aaaaa1234");
    }

    @Test
    public void test_wx() {
        String accessToken = WXAccessTokenUtils.getAccessToken();
        System.out.println(accessToken);
        Message message = new Message();
        message.put("project", "big-market");
        message.put("review", "feat:新加功能");
        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", accessToken);
        sendPostRequest(url, JSON.toJSONString(message));
    }

    public static class Message {
        private String touser = "oA6ii6QAGKSKEhg0JYaFi9HmPrK0";

        private String template_id = "hj3zPEUJGi9TkFQu-DJ7HDAlMXLL93M1F5kQuUloj7E";

        private String url = "https://github.com/yinghe06/openai-code-review-log";
        private Map<String, Map<String, String>> data = new HashMap<>();

        public void put(String key, String value) {
            data.put(key, new HashMap<String, String>() {
                {
                    put("value", value);
                }
            });

        }
        public String getTouser() {
            return touser;
        }

        public void setTouser(String touser) {
            this.touser = touser;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, Map<String, String>> getData() {
            return data;
        }

        public void setData(Map<String, Map<String, String>> data) {
            this.data = data;
        }
    }


    private static void sendPostRequest(String urlstring, String jsonBody) {
        try {
            URL url = new URL(urlstring);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
                String response = scanner.useDelimiter("\\A").next();
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}