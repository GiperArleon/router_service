package com.router;

import com.router.api.soap.notification.NotifyServiceImpl;
import com.router.model.TimeRecord;
import lombok.extern.slf4j.Slf4j;
import ru.soap.teamservice.User;

import javax.xml.ws.Endpoint;
import java.util.List;

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

        NotifyServiceImpl notifyService = new NotifyServiceImpl();

        List<User> result = notifyService.getUsersWithoutTracks(6);
        log.debug("users without records: {}", result.size());
    }
}
