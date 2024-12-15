package com.example.telegrambot.service;

import com.example.telegrambot.entity.Category;
import com.example.telegrambot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Добавить категорию (корневую или дочернюю).
     */
    @Transactional
    public void addCategory(String name, String parentName) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Категория с таким именем уже существует: " + name);
        }

        Category newCategory = Category.builder()
                .name(name)
                .build();

        if (parentName != null) {
            Category parent = categoryRepository.findByName(parentName)
                    .orElseThrow(() -> new RuntimeException("Родительская категория не найдена: " + parentName));
            newCategory.setParent(parent);
            parent.getChildren().add(newCategory);
        }

        categoryRepository.save(newCategory);
        log.info("Category '{}' added (parent: {})", name, parentName);
    }

    /**
     * Удалить категорию (каскадно удалятся дочерние).
     */
    @Transactional
    public void removeCategory(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Категория не найдена: " + name));
        categoryRepository.delete(category);
        log.info("Category '{}' removed", name);
    }

    /**
     * Построить дерево в виде строки.
     */
    @Transactional
    public String getCategoryTree() {
        List<Category> all = categoryRepository.findAll();
        List<Category> rootCategories = all.stream().filter(c -> c.getParent() == null).toList();

        if (rootCategories.isEmpty()) {
            return "Дерево пусто.";
        }

        StringBuilder sb = new StringBuilder();
        for (Category root : rootCategories) {
            buildTreeString(root, 0, sb);
        }
        return sb.toString();
    }

    private void buildTreeString(Category category, int level, StringBuilder sb) {
        sb.append("  ".repeat(level)).append(category.getName()).append("\n");
        for (Category child : category.getChildren()) {
            buildTreeString(child, level + 1, sb);
        }
    }
}
