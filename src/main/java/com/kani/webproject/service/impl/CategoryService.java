package com.kani.webproject.service.impl;

import com.kani.webproject.dto.CategoryDto;
import com.kani.webproject.entity.Category;
import com.kani.webproject.repository.ICategoryRepository;
import com.kani.webproject.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

}
