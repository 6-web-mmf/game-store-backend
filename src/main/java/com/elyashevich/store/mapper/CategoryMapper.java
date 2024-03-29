package com.elyashevich.store.mapper;

import com.elyashevich.store.dto.categoryDto.CategoryCreateDto;
import com.elyashevich.store.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public Category convert(final CategoryCreateDto categoryCreateDto) {
        return Category
                .builder()
                .title(categoryCreateDto.title())
                .build();
    }
}
