package com.example.telegrambot.command;

import com.example.telegrambot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddElementCommand implements BotCommand {

    private final CategoryService categoryService;

    @Override
    public String getCommandName() {
        return "/addElement";
    }

    @Override
    public String execute(String[] args) {
        if (args.length == 1) {
            // Корневая категория
            try {
                categoryService.addCategory(args[0], null);
                return "Корневой элемент '" + args[0] + "' успешно добавлен.";
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        } else if (args.length == 2) {
            // Дочерний элемент
            String parentName = args[0];
            String childName = args[1];
            try {
                categoryService.addCategory(childName, parentName);
                return "Дочерний элемент '" + childName + "' успешно добавлен к '" + parentName + "'.";
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        } else {
            return "Неверный формат. Используйте /addElement <название> или /addElement <родитель> <название>.";
        }
    }
}