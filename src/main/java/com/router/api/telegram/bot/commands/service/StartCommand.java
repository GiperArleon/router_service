package com.router.api.telegram.bot.commands.service;

import com.router.clients.soap.SoapUserClientFactory;
import com.router.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.soap.teamservice.DaoUser;
import ru.soap.teamservice.FindUserByTelegramId;
import ru.soap.teamservice.FindUserByTelegramIdResponse;
import static com.router.api.telegram.bot.BotTextConstants.START_COMMAND;
import static com.router.api.telegram.bot.BotTextConstants.WORK_COMMAND;

@Slf4j
public class StartCommand extends ServiceCommand {

    protected DaoUser daoUser = SoapUserClientFactory.getSoapUserClient();

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        log.info("команда {} пользователь {}", this.getCommandIdentifier(), Utils.getUserFullDescription(user));
        log.debug("Пользователь {}. Начато выполнение команды {}", userName, this.getCommandIdentifier());
        try {
            FindUserByTelegramId findUserByTelegramId = new FindUserByTelegramId();
            findUserByTelegramId.setUserTelegramLogin(user.getId().toString());
            FindUserByTelegramIdResponse response = daoUser.findUserByTelegramId(findUserByTelegramId);
            ru.soap.teamservice.User userRecord = response.getReturn();
            if(userRecord != null) {
                log.info("user {} found by telegram id {} work mode", userRecord.getUsername(), user.getId());
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, WORK_COMMAND);
            } else {
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, START_COMMAND);
            }
        } catch(Exception e) {
            log.error("error {}", e.toString());
        }
        log.debug("Пользователь {}. Завершено выполнение команды {}", userName, this.getCommandIdentifier());
    }
}
