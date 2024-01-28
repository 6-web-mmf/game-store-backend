package com.elyashevich.store.service.impl;

import com.elyashevich.store.dto.categoryDto.CategoryCreateDto;
import com.elyashevich.store.entity.Category;
import com.elyashevich.store.exception.NotFoundException;
import com.elyashevich.store.mapper.CategoryMapper;
import com.elyashevich.store.repository.CategoryRepository;
import com.elyashevich.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category create(CategoryCreateDto categoryCreateDto) {
        final Category category = categoryMapper.convert(categoryCreateDto);
        log.info("CREATE NEW CATEGORY\n" + category);
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(String id) {
        log.info("FIND CATEGORY BY ID");
        return categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Category with id = %s wasn't found", id)));
    }

    @Override
    public Category findByTitle(String title) {
        log.info("FIND CATEGORY BY TITLE");
        return categoryRepository.findByTitle(title).orElseThrow(() ->
                new NotFoundException(String.format("Category with title = %s wasn't found", title)));
    }

    @Override
    public List<Category> findAll() {
        log.info("FIND ALL CATEGORIES");
        return categoryRepository.findAll();
    }

    @Override
    public Category update(String id, CategoryCreateDto categoryCreateDto) {
        final Category category = findById(id);
        category.setTitle(categoryCreateDto.title());
        log.info("UPDATE CATEGORY\n" + category);
        return categoryRepository.save(category);
    }

    @Override
    public void delete(String id) {
        final Category category = findById(id);
        log.info("DELETE CATEGORY\n" + category);
        categoryRepository.delete(category);
    }
}
