package com.router;

import com.router.api.soap.notification.NotifyServiceImpl;
import com.router.api.telegram.bot.Bot;
import com.router.api.telegram.messager.TelegramNotifier;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
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
                .forEach((key, value) -> log.debug("{} = {}", key, value));

        Endpoint endpoint = Endpoint.create(new NotifyServiceImpl());
        endpoint.publish("http://localhost:8066/notification");

        NotifyServiceImpl notifyService = new NotifyServiceImpl();
        List<User> result = notifyService.getUsersWithoutTracks(6);
        log.debug("users without records: {}", result.size());

        String BOT_NAME = PROPERTIES.getProperties().get("telegram.api.user");
        String BOT_TOKEN = PROPERTIES.getProperties().get("telegram.api.token");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(BOT_NAME, BOT_TOKEN));
            TelegramNotifier telegramNotifier = new TelegramNotifier();
            telegramNotifier.sendHello("291852215", BOT_TOKEN);
        } catch(TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
