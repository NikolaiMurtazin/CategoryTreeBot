package com.example.telegrambot.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CommandRegistry {

    private final Map<String, BotCommand> commands = new HashMap<>();

    public CommandRegistry(List<BotCommand> commandList) {
        for (BotCommand cmd : commandList) {
            commands.put(cmd.getCommandName(), cmd);
        }
    }

    public String processCommand(String commandName, String[] args) {
        BotCommand command = commands.get(commandName);
        if (command != null) {
            log.info("Executing command: {}", commandName);
            return command.execute(args);
        } else {
            log.warn("Unknown command: {}", commandName);
            return "Неизвестная команда. Используйте /help.";
        }
    }
}
