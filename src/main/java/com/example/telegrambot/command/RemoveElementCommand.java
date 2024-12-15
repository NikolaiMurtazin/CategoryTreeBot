package com.example.telegrambot.command;

import com.example.telegrambot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveElementCommand implements BotCommand {

    private final CategoryService categoryService;

    @Override
    public String getCommandName() {
        return "/removeElement";
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) {
            return "Неверный формат. Используйте /removeElement <название элемента>";
        }
        try {
            categoryService.removeCategory(args[0]);
            return "Категория '" + args[0] + "' удалена (вместе с дочерними).";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
