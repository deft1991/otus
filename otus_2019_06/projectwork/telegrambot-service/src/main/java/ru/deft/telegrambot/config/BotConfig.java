package ru.deft.telegrambot.config;

/*
 * Created by sgolitsyn on 11/22/19
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "telegram")
@Getter
@Setter
public class BotConfig {

    private String botName;
    private String botToken;

//    @Bean("mAnonymouses")
//    AnonymousService mAnonymouses() {
//        return new AnonymousService();
//    }

    private static final String PROXY_HOST = "185.234.217.6";
    private static final int PROXY_PORT = 3128;
    private static final DefaultBotOptions.ProxyType PROXY_TYPE = DefaultBotOptions.ProxyType.HTTP;

    @Bean("botOptions")
    DefaultBotOptions botOptions() {

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        botOptions.setProxyType(PROXY_TYPE);
        return botOptions;
    }
}
