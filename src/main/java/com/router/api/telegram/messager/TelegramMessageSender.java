package com.router.api.telegram.messager;

import static com.router.tools.PropertyReader.PROPERTIES;

public class TelegramMessageSender {
    TelegramNotifier telegramNotifier = new TelegramNotifier();

    public boolean sendNotificationById(String id, String message) {
        return telegramNotifier.sendMessage(id,
                                            PROPERTIES.getProperties().get("telegram.api.token"),
                                            message);
    }
}
