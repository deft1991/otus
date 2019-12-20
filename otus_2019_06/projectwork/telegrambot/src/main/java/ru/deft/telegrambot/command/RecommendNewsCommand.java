package ru.deft.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.deft.telegrambot.service.AnonymousService;

/*
 * Created by sgolitsyn on 12/20/19
 */
@Slf4j
public class RecommendNewsCommand extends AnonymizerCommand {

    public static final String LOG_RECOMMEND_COMMAND = "RecommendNewsCommand with user: id = %s name = %s, commandIdentifier: %s";

    private final AnonymousService mAnonymouses;

    public RecommendNewsCommand(AnonymousService mAnonymouses) {
        super("recommend", "recommend news for post in all chats\n");
        this.mAnonymouses = mAnonymouses;
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.debug(String.format(LOG_RECOMMEND_COMMAND, user.getId(), user.getUserName(), getCommandIdentifier()));

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)) {
            log.info(String.format("User id = %s  is trying to execute '%s' without starting the bot!", user.getId(), getCommandIdentifier()));
            message.setText("Firstly you should start the bot! Execute '/start' command!");
            execute(absSender, message, user);
            return;
        }
    }
}
