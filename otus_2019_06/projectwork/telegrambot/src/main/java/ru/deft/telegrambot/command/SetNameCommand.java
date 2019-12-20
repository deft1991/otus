package ru.deft.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.deft.telegrambot.service.AnonymousService;

/*
 * Created by sgolitsyn on 11/22/19
 */
@Slf4j
public final class SetNameCommand extends AnonymizerCommand {

    public static final String LOG_COMMAND_WITH_USER_ID_AND_COMMAND_IDENTIFIER = "Command: %s, userId: %s, commandIdentifier: %s";
    private final AnonymousService mAnonymouses;

    public SetNameCommand(AnonymousService anonymouses) {
        super("set_name", "set or change name that will be displayed with your messages\n");
        mAnonymouses = anonymouses;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        log.info(String.format(LOG_COMMAND_WITH_USER_ID_AND_COMMAND_IDENTIFIER, this.getDescription(), user.getId(), getCommandIdentifier()));

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)) {
            log.info(String.format("User id = %s  is trying to execute '%s' without starting the bot!", user.getId(), getCommandIdentifier()));
            message.setText("Firstly you should start the bot! Execute '/start' command!");
            execute(absSender, message, user);
            return;
        }

        String displayedName = getName(strings);

        if (displayedName == null) {
            log.info(String.format("User id = %s is trying to set empty name.", user.getId()));
            message.setText("You should use non-empty name!");
            execute(absSender, message, user);
            return;
        }

        StringBuilder sb = new StringBuilder();

        if (mAnonymouses.setUserDisplayedName(user, displayedName)) {

            if (mAnonymouses.getDisplayedName(user) == null) {
                log.info(String.format("User id = %s set a name '%s'", user.getId(), displayedName));
                sb.append("Your displayed name: '").append(displayedName)
                        .append("'. Now you can send messages to bot!");
            } else {
                log.info(String.format("User id = %s has changed name to '%s'", user.getId(), displayedName));
                sb.append("Your new displayed name: '").append(displayedName).append("'.");
            }
        } else {
            log.debug(String.format("User id = %s is trying to set taken name '%s'", user.getId(), displayedName));
            sb.append("Name ").append(displayedName).append(" is already in use! Choose another name!");
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }

    private String getName(String[] strings) {

        if (strings == null || strings.length == 0) {
            return null;
        }

        String name = String.join(" ", strings);
        return name.replaceAll(" ", "").isEmpty() ? null : name;
    }
}
