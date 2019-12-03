package ru.deft.telegrambot;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.deft.telegrambot.bot.AnonymizerBot;


@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
//@EnableConfigurationProperties
public class TelegrambotApplication {

    private static final String PROXY_HOST = "185.234.217.6";
    private static final int PROXY_PORT = 3128;
    private static final DefaultBotOptions.ProxyType PROXY_TYPE = DefaultBotOptions.ProxyType.HTTP;

    public static void main(String[] args) {
        SpringApplication.run(TelegrambotApplication.class, args);
      new TelegrambotApplication().init();
    }

    public void init() {
        try {

            log.info("Initializing API context...");
            ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();

            log.info("Configuring bot options...");
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            botOptions.setProxyType(PROXY_TYPE);

            log.info("Registering Anonymizer...");
            botsApi.registerBot(new AnonymizerBot(botOptions));

            log.info("Anonymizer bot is ready for work!");

        } catch (TelegramApiRequestException e) {
            log.error("Error while initializing bot!", e);
        }
    }
}
