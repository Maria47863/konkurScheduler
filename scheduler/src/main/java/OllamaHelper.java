package org.example;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.json.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.OllamaResponse;

public class OllamaHelper {
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static boolean isdone = false;

    public static String getStudyTips(String lessonName, int score) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String prompt = "من یک دانش آموز هستم که می خواهم در درس " + lessonName +
                " پیشرفت کنم. درصد من در آزمون اخیر " + score +
                "٪ بوده است. چه راهکارهایی برای مطالعه بهتر پیشنهاد می دهی؟ ";
        JSONObject json = new JSONObject();
        json.put("model", "llama3.2");
        json.put("prompt", prompt);

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(OLLAMA_URL)
                .post(body)
                .build();

        StringBuilder fullResponse = new StringBuilder();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()) {
            String responseBody = response.body().string();
            String[] words = responseBody.split("\n");
            int i = 0;
            while (!isdone) {
                //System.out.println(responseBody);
                OllamaResponse ollamaResponse = new ObjectMapper().readValue(words[i], OllamaResponse.class);
                System.out.print(ollamaResponse.getResponse());
                isdone = ollamaResponse.isDone();
                i++;
            }
                //JSONObject jsonResponse = new JSONObject(responseBody);
                //String responseText = jsonResponse.optString("response", "");
                //fullResponse.append(responseText);
                //Object doneObject = jsonResponse.get("done");
                //System.out.println("Full JSON Response: " + jsonResponse.toString());
                //boolean doneValue = jsonResponse.optBoolean("done");
                //System.out.println("Extracted 'done' value before update " + doneValue);
                //System.out.println("isDone before update: " + isDone);
                //if(doneValue && !isDone) {
                   // isDone = true;
                //}
                //System.out.println("isDone after update: " + isDone);
            //} else {
                //throw new IOException("Unexpected code " + response);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        //if(!isDone) {
            //System.out.println("Timeout! No 'done: true' received.");
        //}

        //System.out.println(fullResponse.toString());
        //return fullResponse.toString();
        return "";
    }
}
