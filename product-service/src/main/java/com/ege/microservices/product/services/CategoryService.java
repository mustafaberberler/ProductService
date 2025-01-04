package com.ege.microservices.product.services;

import com.ege.microservices.product.services.dtos.CategoryDto;
import com.ege.microservices.product.services.dtos.CategoryRequestDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryRequestDto categoryRequestDto);

    String deleteCategory(CategoryRequestDto categoryRequestDto);

    CategoryDto updateCategory(String categoryName, CategoryRequestDto categoryRequestDto);

    CategoryDto getCategoryByName(String categoryName);

    List<CategoryDto> getAllCategories();

}
