package com.router.tools;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static long timeInSec(long nanotime) {
        return TimeUnit.SECONDS.convert(nanotime, TimeUnit.NANOSECONDS);
    }

    public static String getUserName(Message msg) {
        return getUserName(msg.getFrom());
    }

    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    public static String getUserFullDescription(User user) {
        return (user.getUserName() != null) ? user.getUserName() + " id = " + user.getId():
                String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getId());
    }
}
