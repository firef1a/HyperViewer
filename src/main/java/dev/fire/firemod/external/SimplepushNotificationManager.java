package dev.fire.firemod.external;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SimplepushNotificationManager {
    private String apiKey;

    public SimplepushNotificationManager(String apiKey) {
        this.apiKey = apiKey;
    }

    public void sendMobileNotification(String title, String body, String event) throws IOException, InterruptedException {
        HashMap<String, String> requestBody = new HashMap<String, String>() {{
            put("key", apiKey);
            put("title", title);
            put("msg", body);
            put("event", event);
        }};
        internalSendNotification(requestBody);
    }

    public void sendMobileNotification(String title, String body) throws IOException, InterruptedException {
        HashMap<String, String> requestBody = new HashMap<String, String>() {{
            put("key", apiKey);
            put("title", title);
            put("msg", body);
        }};
        internalSendNotification(requestBody);
    }

    private void internalSendNotification(HashMap<String, String> requestBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.simplepush.io/send"))
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(requestBody)))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }
}
