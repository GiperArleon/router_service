package com.router.api.telegram.bot.commands.user;

import com.router.api.telegram.bot.commands.OperationCommand;
import com.router.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.soap.teamservice.Group;
import ru.soap.teamservice.Role;
import static com.router.tools.Utils.getUserName;

@Slf4j
public class RegCommand extends OperationCommand {

    public RegCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = getUserName(user);
        log.debug("Пользователь {}. Начато выполнение команды {}", userName, this.getCommandIdentifier());
        log.info("команда {} пользователь {}", this.getCommandIdentifier(), Utils.getUserFullDescription(user));

        if(strings.length<2) {
            log.error("wrong parameters:");
            for(String str: strings)
                log.error(str);
            sendError(absSender, chat.getId(), this.getCommandIdentifier(), userName);
        }

        Group group = new Group();
        group.setGId(1);
        group.setGroup("some group");

        Role role = new Role();
        role.setRId(3);
        role.setRoleName("lector");

        ru.soap.teamservice.User userRecord = new ru.soap.teamservice.User();
        userRecord.setUsername(strings[0]);
        userRecord.setLogin(strings[0]);
        userRecord.setSurname(strings[1]);
        userRecord.setGroup(group);
        userRecord.setPhone(strings[1]);
        userRecord.setRole(role);
        userRecord.setTelegramId(user.getId());
        userRecord.setTelegramUser(getUserName(user));

        if(daoUser.saveUser(userRecord)) {
            sendOK(absSender, chat.getId(), this.getCommandIdentifier(), userName);
        } else {
            sendError(absSender, chat.getId(), this.getCommandIdentifier(), userName);
        }
        log.debug("Пользователь {}. Завершено выполнение команды {}", userName, this.getCommandIdentifier());
    }
}
