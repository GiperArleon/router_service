package com.router.clients.rest;

import com.router.model.TimeRecord;
import lombok.extern.slf4j.Slf4j;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import static com.router.clients.rest.RestRequestUrls.GET_USER_RECORDS_URL;

@Slf4j
public class RestRequestHandler {
    private final HttpClient client;
    private final JsonParser jsonParser;

    public RestRequestHandler() {
        this.client = HttpClient.newHttpClient();
        this.jsonParser = new JsonParser();
    }

    public List<TimeRecord> getUserRecords(Integer userId, Integer days) {
        HttpResponse<String> response;
        List<TimeRecord> result = new ArrayList<>();
        String urlStr = String.format(GET_USER_RECORDS_URL, userId, days);
        try {
            log.debug("send rest request {}", urlStr);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                log.debug("got response code {} body {}", response.statusCode(), response.body());
                result = jsonParser.getListOfObjectsFromJson(response.body());        //getResponseEntity(response.body(), List<TimeRecord>.class);
                log.debug("parsing result: {}", result);
            } else {
                log.error("wrong response code = {}", response.statusCode());
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
