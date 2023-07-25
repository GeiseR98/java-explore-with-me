package ru.practicum.main.category.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminCategoryController {
    private final CategoryService catService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.debug("Попытка добавить новую категорию");
        return catService.save(categoryDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catId}")
    public void removeById(@PathVariable Integer catId) {
        log.debug("Попытка удаления категории с идентификатором {}", catId);
        catService.removeById(catId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{catId}")
    public CategoryDto changeCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.debug("Попытка изменить категорию");
        return catService.changeCategory(categoryDto);
    }
}
