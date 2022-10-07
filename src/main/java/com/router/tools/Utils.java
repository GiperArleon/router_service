package com.router.tools;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import static com.router.api.telegram.bot.BotTextConstants.*;

public class Utils {
    public static final int MINUTES_IN_HOUR = 60;
    public static final int MINUTES_IN_DAY = 1440;

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

    public static String validateTime(Integer hours, Integer minutes) {
        int dayTime = hours*MINUTES_IN_HOUR + minutes;
        if(hours<0 || minutes<0)
            return VALIDATOR_NEGATIVE_TIME;
        if(minutes>MINUTES_IN_HOUR)
            return VALIDATOR_MAX_MINUTES;
        if(dayTime>MINUTES_IN_DAY)
            return VALIDATOR_MAX_TIME;
        return VALIDATOR_OK;
    }
}
