package com.router;

import com.router.api.soap.notification.NotifyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import javax.xml.ws.Endpoint;
import static com.router.tools.PropertyReader.PROPERTIES;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("* * *");

        PROPERTIES
          .getProperties()
          .forEach((key, value) -> log.info("{} = {}", key, value));

        Endpoint endpoint = Endpoint.create(new NotifyServiceImpl());
        endpoint.publish("http://localhost:8066/notification");
    }
}
