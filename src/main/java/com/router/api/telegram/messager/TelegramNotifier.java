package com.router.api.telegram.messager;

import lombok.extern.slf4j.Slf4j;
import javax.ws.rs.core.UriBuilder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import static com.router.api.telegram.bot.BotTextConstants.BOT_STARTED_MESSAGE;

@Slf4j
public class TelegramNotifier {

    public void sendHello(String CHAT_ID, String TOKEN) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", BOT_STARTED_MESSAGE);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("TelegramNotifier response {} body {}", response.statusCode(), response.body());
        } catch(Exception ex) {
            log.error("ex {}", ex.getMessage());
        }
    }
}
