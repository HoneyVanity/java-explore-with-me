package ru.practicum.ewm.category.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CreateCategoryDto;
import ru.practicum.ewm.category.dto.UpdateCategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/categories")
public class CategoryControllerAdmin {
    CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody CreateCategoryDto createCategoryDto) {
        return categoryService.createCategory(createCategoryDto);
    }

    @PatchMapping("/{categoryId}")
    public CategoryDto updateCategory(
            @PathVariable long categoryId,
            @Valid @RequestBody UpdateCategoryDto updateCategoryDto
    ) {
        return categoryService.updateCategory(categoryId, updateCategoryDto);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}