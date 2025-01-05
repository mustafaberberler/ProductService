package com.ege.microservices.product.services.impl;

import com.ege.microservices.product.convert.DTOConverters.CategoryDTOConverter;
import com.ege.microservices.product.entities.CategoryEntity;
import com.ege.microservices.product.repositories.CategoryRepository;
import com.ege.microservices.product.services.CategoryService;
import com.ege.microservices.product.services.dtos.CategoryDto;
import com.ege.microservices.product.services.dtos.CategoryRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryDTOConverter categoryDTOConverter;

    private final RestTemplate restTemplate;

    @Override
    public CategoryDto createCategory(CategoryRequestDto categoryRequestDto) {

        CategoryEntity foundEntity = categoryRepository.findCategoryEntityByCategoryName(categoryRequestDto.getCategoryName());

        if(foundEntity != null){
            throw new ResourceNotFoundException("Category already exists: {}", categoryRequestDto.getCategoryName());
        }

        log.info("Creating category: {}", categoryRequestDto.getCategoryName());
        CategoryEntity categoryEntity = categoryDTOConverter.convertCategoryRequestDtoToCategoryEntity(categoryRequestDto);

        CategoryEntity savedEntity = categoryRepository.save(categoryEntity);

        log.info("Category created with ID: {} and NAME: {}", savedEntity.getCategoryId(), savedEntity.getCategoryName());

        return categoryDTOConverter.convertCategoryEntityToCategoryDto(savedEntity);

    }

    @Override
    public String deleteCategory(CategoryRequestDto categoryRequestDto) {
        log.info("Deleting category with NAME: {}", categoryRequestDto.getCategoryName());

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByCategoryName(categoryRequestDto.getCategoryName());

        if(categoryEntity == null){
            throw new ResourceNotFoundException("Category not found: {}", categoryRequestDto.getCategoryName());
        }

        categoryRepository.deleteCategoryByCategoryName(categoryRequestDto.getCategoryName());

        log.info("Category with NAME: {} deleted.", categoryRequestDto.getCategoryName());

        return "Category with NAME: " + categoryRequestDto.getCategoryName() + " has been deleted successfully.";

    }

    @Override
    public CategoryDto updateCategory(String categoryName, CategoryRequestDto categoryRequestDto) {
        log.info("Updating category with NAME: {}", categoryName);

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByCategoryName(categoryName);

        if(categoryEntity == null){
            throw new ResourceNotFoundException("Category not found: {}", categoryRequestDto.getCategoryName());
        }

        categoryEntity.setCategoryName(categoryRequestDto.getCategoryName());

        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);

        log.info("Category with NAME {} updated.", categoryName);

        return categoryDTOConverter.convertCategoryEntityToCategoryDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategoryByName(String categoryName) {

        String url = "http://localhost:8085/api/category/get/name/" + categoryName;

        ResponseEntity<CategoryDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CategoryDto>() {}
        );

        return response.getBody();

    }

    @Override
    public List<CategoryDto> getAllCategories() {

        String url = "http://localhost:8085/api/category/getAll";

        ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CategoryDto>>() {}
        );

        return response.getBody();
    }
}

