package com.ege.microservices.product.controllers.impls;

import com.ege.microservices.product.controllers.CategoryController;
import com.ege.microservices.product.controllers.requests.CategoryRequestModel;
import com.ege.microservices.product.controllers.responses.CategoryModel;
import com.ege.microservices.product.convert.ModelConverters.CategoryModelConverter;
import com.ege.microservices.product.services.CategoryService;
import com.ege.microservices.product.services.dtos.CategoryDto;
import com.ege.microservices.product.services.dtos.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    private final CategoryModelConverter categoryModelConverter;

    @Override
    public ResponseEntity<CategoryModel> createCategory(CategoryRequestModel categoryRequestModel) {

        CategoryRequestDto categoryRequestDto = categoryModelConverter.convertCategoryRequestModelToCategoryRequestDto(categoryRequestModel);

        CategoryDto categoryDto = categoryService.createCategory(categoryRequestDto);

        CategoryModel categoryModel = categoryModelConverter.convertCategoryDtoToCategoryModel(categoryDto);

        return new ResponseEntity<>(categoryModel, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteCategory(CategoryRequestModel categoryRequestModel) {

        CategoryRequestDto categoryRequestDto = categoryModelConverter.convertCategoryRequestModelToCategoryRequestDto(categoryRequestModel);

        String deleteMessage = categoryService.deleteCategory(categoryRequestDto);

        return new ResponseEntity<>(deleteMessage, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CategoryModel> updateCategory(String categoryName, CategoryRequestModel categoryRequestModel) {

        CategoryRequestDto categoryRequestDto = categoryModelConverter.convertCategoryRequestModelToCategoryRequestDto(categoryRequestModel);

        CategoryDto categoryDto = categoryService.updateCategory(categoryName, categoryRequestDto);

        CategoryModel categoryModel = categoryModelConverter.convertCategoryDtoToCategoryModel(categoryDto);

        return new ResponseEntity<>(categoryModel, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CategoryModel> getCategoryByName(String categoryName) {

        CategoryDto categoryDto = categoryService.getCategoryByName(categoryName);

        CategoryModel categoryModel = categoryModelConverter.convertCategoryDtoToCategoryModel(categoryDto);

        return new ResponseEntity<>(categoryModel, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<CategoryModel>> getAllCategories() {

        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();

        List<CategoryModel> categoryModelList = categoryModelConverter.convertCategoryDtoListToCategoryModelList(categoryDtoList);

        return new ResponseEntity<>(categoryModelList, HttpStatus.OK);

    }
}
