package cn.heying.plus.sdk;
import cn.heying.plus.sdk.domain.model.ChatCompletionSyncResponseDTO;
import cn.heying.plus.sdk.types.utils.BearerTokenUtils;
import com.alibaba.fastjson2.JSON;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OpenAiCodeReview {
    public static void main(String[] args) throws Exception {
        System.out.println("测试");
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD-1", "HEAD");
        processBuilder.directory(new File("."));

       Process process= processBuilder.start();
       BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
       String line;
        StringBuilder diffCode= new StringBuilder();
        while((line=reader.readLine())!=null){
            diffCode.append(line);
        }
        String log=codeReview(diffCode.toString());
        System.out.println("code review"+log);
    }
    public static String codeReview(String diffCode) throws Exception {
        String apiKeyScrect = "f9b9e08c2f9bb195a620812dee4c3507.mwUfxgeng6lSF3cS";
        String token = BearerTokenUtils.getToken(apiKeyScrect);
        //System.out.println(token);
        URL url = new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        con.setDoOutput(true);
        String jsonInpuString = "{"
                + "\"model\":\"glm-4-flash\","
                + "\"messages\": ["
                + "    {"
                + "        \"role\": \"user\","
                + "        \"content\": \"你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码为: " + diffCode + "\""
                + "    }"
                + "]"
                + "}";


        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInpuString.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        int responseCode = con.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        ChatCompletionSyncResponseDTO response = JSON.parseObject(content.toString(), ChatCompletionSyncResponseDTO.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
