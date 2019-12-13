package ru.deft.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/*
 * Created by sgolitsyn on 11/22/19
 */
@Slf4j
abstract class AnonymizerCommand extends BotCommand {

    AnonymizerCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    void execute(AbsSender sender, SendMessage message, User user) {
        try {
            sender.execute(message);
//            log.debug(Level.getLevel(LogLevel.SUCCESS.getValue()), LogTemplate.COMMAND_SUCCESS.getTemplate(), user.getId(), getCommandIdentifier());
        } catch (TelegramApiException e) {
//            log.error(LogTemplate.COMMAND_EXCEPTION.getTemplate(), user.getId(), getCommandIdentifier(), e);
        }
    }
}
