package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.CategoryDto;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);

    void removeById(Integer catId);

    CategoryDto changeCategory(CategoryDto categoryDto);
}
