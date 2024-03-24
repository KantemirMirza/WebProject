package com.kani.webproject.service;

import com.kani.webproject.dto.CategoryDto;
import com.kani.webproject.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDto categoryDto);
    List<Category> allCategories();
}

