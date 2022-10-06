package com.router.api.soap.sender;

import com.router.api.telegram.messager.TelegramMessageSender;
import com.router.api.telegram.messager.TelegramMessageSenderFactory;
import com.router.clients.rest.RestAccountantClient;
import com.router.clients.rest.RestAccountantClientFactory;
import com.router.clients.rest.model.TimeRecord;
import com.router.clients.soap.SoapUserClientFactory;
import com.router.clients.soap.UserRoles;
import lombok.extern.slf4j.Slf4j;
import ru.soap.teamservice.DaoUser;
import ru.soap.teamservice.User;
import javax.jws.WebService;
import java.util.List;

@Slf4j
@WebService(endpointInterface = "com.router.api.soap.sender.SenderService")
public class SenderServiceImpl implements SenderService {

    TelegramMessageSender telegramMessageSender = TelegramMessageSenderFactory.getTelegramMessageSender();
    DaoUser daoUser = SoapUserClientFactory.getSoapUserClient();
    RestAccountantClient restAccountantClient = RestAccountantClientFactory.getRestAccountantClient();

    @Override
    public User[] getAllUsers() {
        log.debug("getAllUsers called");
        List<User> users = daoUser.findAllUsers();
        return users.toArray(new User[0]);
    }

    @Override
    public TimeRecord[] getRecordsForToday(Long userId) {
        log.debug("getTodayRecords called");
        List<TimeRecord> records = restAccountantClient.getRecords(userId, 0);
        return records.toArray(new TimeRecord[0]);
    }

    @Override
    public boolean sendTxtReportToLectors(String report) {
        log.debug("sendTxtReportToLectors called");
        List<User> users = daoUser.findAllUsers();
        for(User user: users) {
            if(user.getRole().getRId()>= UserRoles.LECTOR.ordinal()) {
                telegramMessageSender.sendNotificationById(String.valueOf(user.getTelegramId()), report);
            }
        }
        return true;
    }

    @Override
    public boolean sendPdfReportToLectors(String report) {
        log.debug("sendPdfReportToLectors called");
        List<User> users = daoUser.findAllUsers();
        for(User user: users) {
            if(user.getRole().getRId()>= UserRoles.LECTOR.ordinal()) {
                telegramMessageSender.sendNotificationById(String.valueOf(user.getTelegramId()), report);
            }
        }
        return true;
    }
}
