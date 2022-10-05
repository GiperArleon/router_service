package com.router.api.soap.notification;

import com.router.api.telegram.messager.TelegramMessageSender;
import com.router.api.telegram.messager.TelegramMessageSenderFactory;
import com.router.clients.rest.RestAccountantClient;
import com.router.clients.rest.RestAccountantClientFactory;
import com.router.clients.soap.SoapUserClientFactory;
import com.router.clients.rest.model.TimeRecord;
import com.router.clients.soap.UserRoles;
import lombok.extern.slf4j.Slf4j;
import ru.soap.teamservice.DaoUser;
import ru.soap.teamservice.User;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@WebService(endpointInterface = "com.router.api.soap.notification.NotifyService")
public class NotifyServiceImpl implements NotifyService {

    TelegramMessageSender telegramMessageSender = TelegramMessageSenderFactory.getTelegramMessageSender();
    DaoUser daoUser = SoapUserClientFactory.getSoapUserClient();
    RestAccountantClient restAccountantClient = RestAccountantClientFactory.getRestAccountantClient();

    @Override
    public User[] getUsersWithoutTracks(Integer days) {
        log.debug("getUsersWithoutTracks called");
        ArrayList<User> result = new ArrayList<>();
        List<User> users = daoUser.findAllUsers();
        for(User user: users) {
            List<TimeRecord> records = restAccountantClient.getRecords((long)user.getId(), days);
            if(records.isEmpty()) {
                result.add(user);
            }
        }
        return result.toArray(new User[0]);
    }

    @Override
    public boolean sendNotificationToLeads(String message) {
        log.debug("sendNotificationToLead called");
        List<User> users = daoUser.findAllUsers();
        for(User user: users) {
            if(user.getRole().getRId()>= UserRoles.LEAD.ordinal()) {
                telegramMessageSender.sendNotificationById(String.valueOf(user.getTelegramId()), message);
            }
        }
        return true;
    }

    @Override
    public boolean sendNotificationToLectors(String message) {
        log.debug("sendNotificationToLector called");
        List<User> users = daoUser.findAllUsers();
        for(User user: users) {
            if(user.getRole().getRId()>= UserRoles.LECTOR.ordinal()) {
                telegramMessageSender.sendNotificationById(String.valueOf(user.getTelegramId()), message);
            }
        }
        return true;
    }
}
