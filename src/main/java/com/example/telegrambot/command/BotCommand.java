package com.example.telegrambot.command;

public interface BotCommand {
    String getCommandName();
    String execute(String[] args);
}
