package com.router.api.soap.sender;

import com.router.api.telegram.bot.Bot;
import com.router.api.telegram.bot.TelegramBotFactory;
import com.router.api.telegram.messager.TelegramMessageSender;
import com.router.api.telegram.messager.TelegramMessageSenderFactory;
import com.router.clients.rest.RestAccountantClient;
import com.router.clients.rest.RestAccountantClientFactory;
import com.router.clients.rest.model.TimeRecord;
import com.router.clients.soap.SoapUserClientFactory;
import com.router.clients.soap.UserRoles;
import com.router.pdf.PdfManager;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.soap.teamservice.DaoUser;
import ru.soap.teamservice.User;
import javax.jws.WebService;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@WebService(endpointInterface = "com.router.api.soap.sender.SenderService")
public class SenderServiceImpl implements SenderService {

    TelegramMessageSender telegramMessageSender = TelegramMessageSenderFactory.getTelegramMessageSender();
    DaoUser daoUser = SoapUserClientFactory.getSoapUserClient();
    RestAccountantClient restAccountantClient = RestAccountantClientFactory.getRestAccountantClient();
    Bot bot = TelegramBotFactory.getTelegramBot();

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
        log.info("sendTxtReportToLectors called");
        List<User> users = daoUser.findAllUsers();
        for(User user: users) {
            if(user.getRole().getRId() >= UserRoles.LECTOR.ordinal()) {
                telegramMessageSender.sendNotificationById(String.valueOf(user.getTelegramId()), report);
            }
        }
        return true;
    }

    @Override
    public boolean sendPdfReportToLectors(String report) {
        log.info("sendPdfReportToLectors called");
        try {
            String fileName = PdfManager.prepearePdf(report);
            List<User> users = daoUser.findAllUsers();
            File file = new File(fileName);
            InputFile inputFile = new InputFile();
            inputFile.setMedia(file, LocalDate.now().toString().concat("_report.pdf"));
            for(User user: users) {
                if(user.getRole().getRId() >= UserRoles.LECTOR.ordinal()) {
                    SendDocument response = new SendDocument(String.valueOf(user.getTelegramId()), inputFile);
                    bot.execute(response);
                }
            }
            return true;
        } catch(Exception e) {
            log.error("send pdf ex {}", e.toString());
            return false;
        }
    }
}
