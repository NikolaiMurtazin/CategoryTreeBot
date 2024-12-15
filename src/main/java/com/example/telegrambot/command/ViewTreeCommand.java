package com.example.telegrambot.command;

import com.example.telegrambot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewTreeCommand implements BotCommand {

    private final CategoryService categoryService;

    @Override
    public String getCommandName() {
        return "/viewTree";
    }

    @Override
    public String execute(String[] args) {
        return categoryService.getCategoryTree();
    }
}
