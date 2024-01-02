package com.elyashevich.store.controller;

import com.elyashevich.store.dto.categoryDto.CategoryCreateDto;
import com.elyashevich.store.entity.Category;
import com.elyashevich.store.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(final @RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        return categoryService.create(categoryCreateDto);
    }

    @GetMapping("/{id}")
    public Category findById(final @PathVariable String id) {
        return categoryService.findById(id);
    }

    @PatchMapping("/{id}")
    public Category update(final @PathVariable String id, final @Valid CategoryCreateDto categoryCreateDto) {
        return categoryService.update(id, categoryCreateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final @PathVariable String id) {
        categoryService.delete(id);
    }
}
