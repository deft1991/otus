package ru.deft.telegrambot.config;

/*
 * Created by sgolitsyn on 11/22/19
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "telegram")
@Getter
@Setter
public class BotConfig {

    private String botName;
    private String botToken;
}
