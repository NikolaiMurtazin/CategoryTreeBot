package com.example.telegrambot.bot;
import com.example.telegrambot.command.CommandRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class CategoryBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final CommandRegistry commandRegistry;

    public CategoryBot(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                String messageText = update.getMessage().getText().trim();
                Long chatId = update.getMessage().getChatId();

                log.info("Received message: {}", messageText);

                // Парсим команду
                String[] parts = messageText.split("\\s+");
                String command = parts[0];
                String[] args = new String[parts.length - 1];
                if (parts.length > 1) {
                    System.arraycopy(parts, 1, args, 0, parts.length - 1);
                }

                String response = commandRegistry.processCommand(command, args);
                sendResponse(chatId, response);
            }
        } catch (Exception e) {
            // Глобальная обработка ошибок
            log.error("Unhandled exception in onUpdateReceived", e);
            if (update.hasMessage()) {
                Long chatId = update.getMessage().getChatId();
                sendResponse(chatId, "Произошла непредвиденная ошибка. Повторите попытку позже.");
            }
        }
    }

    private void sendResponse(Long chatId, String response) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(response);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending response", e);
        }
    }
}
