package com.router;

import com.router.api.soap.notification.NotifyServiceImpl;
import com.router.api.telegram.bot.Bot;
import com.router.api.telegram.messager.TelegramMessageSender;
import com.router.api.telegram.messager.TelegramMessageSenderFactory;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.soap.teamservice.User;
import javax.xml.ws.Endpoint;
import java.util.List;
import static com.router.api.telegram.bot.BotTextConstants.BOT_STARTED_MESSAGE;
import static com.router.tools.PropertyReader.PROPERTIES;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
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
            String DEV_ID = PROPERTIES.getProperties().get("telegram.developer.id");
            TelegramMessageSender telegramMessageSender = TelegramMessageSenderFactory.getTelegramMessageSender();

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(BOT_NAME, BOT_TOKEN));
            if(!telegramMessageSender.sendNotificationById(DEV_ID, BOT_STARTED_MESSAGE))
                log.error("error in sending start message to dev id {}", DEV_ID);

            if(!notifyService.sendNotificationToLeads("test message to leads"))
                log.error("error in sending message to leads");
            if(!notifyService.sendNotificationToLectors("test message to lectors"))
                log.error("error in sending message to lectors");
        } catch(Throwable e) {
            log.error(e.toString());
        }
    }
}
