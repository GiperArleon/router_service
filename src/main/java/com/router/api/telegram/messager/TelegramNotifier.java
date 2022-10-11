package com.router.api.telegram.messager;

import lombok.extern.slf4j.Slf4j;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import static com.router.tools.PropertyReader.PROPERTIES;

@Slf4j
public class TelegramNotifier {
    HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .version(HttpClient.Version.HTTP_2)
            .build();

    public boolean sendMessage(String CHAT_ID, String TOKEN, String message) {

        UriBuilder builder = UriBuilder
                .fromUri(PROPERTIES.getProperties().get("telegram.api.url"))
                .path("/{token}/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", message);

        URI uri = builder.build("bot" + TOKEN);
        log.info(uri.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("TelegramNotifier response {} body {}", response.statusCode(), response.body());
            return response.statusCode() == 200;
        } catch(Exception ex) {
            log.error("ex {}", ex.getMessage());
        }
        return false;
    }
}
