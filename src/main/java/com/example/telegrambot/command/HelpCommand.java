package com.example.telegrambot.command;

import org.springframework.stereotype.Service;

@Service
public class HelpCommand implements BotCommand {

    @Override
    public String getCommandName() {
        return "/help";
    }

    @Override
    public String execute(String[] args) {
        return String.join("\n",
                "/viewTree - Показать дерево категорий.",
                "/addElement <название> - Добавить корневой элемент.",
                "/addElement <родитель> <название> - Добавить дочерний элемент.",
                "/removeElement <название> - Удалить элемент (с дочерними).",
                "/help - Список команд."
        );
    }
}