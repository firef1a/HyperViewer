package dev.fire.firemod.screen.external;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhookManager {

    public static void sendWebhookMessage(String content) throws IOException {
            String webhookURL = "https://discord.com/api/webhooks/1260806119295811695/BJD5dW_MvkqpJN8qHZugeulIJbhFeh0hyVOjk_ITQ5ZEfJ-GGS4wsdXz3qRvGftBrMwG";
            HttpsURLConnection connection = (HttpsURLConnection) new URL(webhookURL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
            connection.setDoOutput(true);
            try(
                final OutputStream outputStream = connection.getOutputStream())

                {
                    //String preparedCommand = content.replaceAll("\\\\", "");
                    //if (preparedCommand.endsWith(" *")) preparedCommand = preparedCommand.substring(0, preparedCommand.length() - 2) + "*";
                    outputStream.write(("{\"content\":\"" + content + "\"}").getBytes(StandardCharsets.UTF_8));
                }
            connection.getInputStream();

    }

    public String getUserMentionFromID(String id) {
        return "<@"+id+">";
    }

    /*
    public static void sendCommand(String content) throws IOException, InterruptedException {
        String webhookURL = "https://discord.com/api/webhooks/1260806119295811695/BJD5dW_MvkqpJN8qHZugeulIJbhFeh0hyVOjk_ITQ5ZEfJ-GGS4wsdXz3qRvGftBrMwG";

        HashMap<String, String> requestBody = new HashMap<String, String>() {{
            put("Content-Type", "application/json");
            put("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
            put("content", "balls");
        }};

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webhookURL))
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(requestBody)))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

     */


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

