package com.router.api.telegram.bot.commands.user;

import com.router.api.telegram.bot.commands.OperationCommand;
import com.router.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.soap.teamservice.FindUserByTelegramId;
import ru.soap.teamservice.FindUserByTelegramIdResponse;
import ru.soap.teamservice.Group;
import ru.soap.teamservice.Role;
import static com.router.api.telegram.bot.BotTextConstants.*;
import static com.router.tools.Utils.getUserName;

@Slf4j
public class RegCommand extends OperationCommand {

    public static final int MAX_PARAMS = 2;

    public RegCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = getUserName(user);
        log.debug("Пользователь {}. Начато выполнение команды {}", userName, this.getCommandIdentifier());
        log.info("команда {} пользователь {}", this.getCommandIdentifier(), Utils.getUserFullDescription(user));

        if(strings.length < MAX_PARAMS) {
            log.error("wrong parameters:");
            for(String str: strings)
                log.error(str);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, BOT_ERROR_REG_WRONG_PARAMS);
            return;
        }

        String res = Utils.validateText(strings[0]);
        if(!res.equals(VALIDATOR_OK)) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, res);
            return;
        }
        res = Utils.validateText(strings[1]);
        if(!res.equals(VALIDATOR_OK)) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, res);
            return;
        }

        try {
            FindUserByTelegramId findUserByTelegramId = new FindUserByTelegramId();
            findUserByTelegramId.setUserTelegramLogin(user.getId().toString());
            FindUserByTelegramIdResponse response = daoUser.findUserByTelegramId(findUserByTelegramId);
            ru.soap.teamservice.User userRecord = response.getReturn();

            if(userRecord != null && userRecord.getUsername() != null) {
                log.info("user {} found by telegram id {} work mode", userRecord.getUsername(), user.getId());
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, WORK_COMMAND);
                return;
            }

            Group group = new Group();
            group.setGId(0);
            group.setGroup("some group");

            Role role = new Role();
            role.setRId(3);
            role.setRoleName("lector");

            ru.soap.teamservice.User userR = new ru.soap.teamservice.User();
            userR.setUsername(strings[0]);
            userR.setLogin(strings[0]);
            userR.setSurname(strings[1]);
            userR.setGroup(group);
            userR.setPhone(strings[1]);
            userR.setRole(role);
            userR.setTelegramId(user.getId());
            userR.setTelegramUser(getUserName(user));

            if(daoUser.saveUser(userR)) {
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, SUCCESS_REG_COMMAND);
            } else {
                sendError(absSender, chat.getId(), this.getCommandIdentifier(), userName);
            }
        } catch(Exception e) {
            log.error("error {}", e.toString());
        }
        log.debug("Пользователь {}. Завершено выполнение команды {}", userName, this.getCommandIdentifier());
    }
}
