package com.example.telegrambot;

import com.example.telegrambot.bot.CategoryBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class CategoryTreeBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryTreeBotApplication.class, args);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(CategoryBot categoryBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(categoryBot);
        return botsApi;
    }
}