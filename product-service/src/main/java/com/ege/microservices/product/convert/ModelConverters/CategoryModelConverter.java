package com.ege.microservices.product.convert.ModelConverters;


import com.ege.microservices.product.controllers.requests.CategoryRequestModel;
import com.ege.microservices.product.controllers.responses.CategoryModel;
import com.ege.microservices.product.services.dtos.CategoryDto;
import com.ege.microservices.product.services.dtos.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryModelConverter {

    private final ModelMapper modelMapper;

    public CategoryRequestDto convertCategoryRequestModelToCategoryRequestDto(CategoryRequestModel categoryRequestModel){

        CategoryRequestDto categoryRequestDto = modelMapper.map(categoryRequestModel, CategoryRequestDto.class);

        return categoryRequestDto;
    }

    public CategoryModel convertCategoryDtoToCategoryModel(CategoryDto categoryDto){

        CategoryModel categoryModel = modelMapper.map(categoryDto, CategoryModel.class);

        return categoryModel;
    }

    public List<CategoryModel> convertCategoryDtoListToCategoryModelList(List<CategoryDto> categoryDtoList){

        List<CategoryModel> categoryModelList = modelMapper.map(categoryDtoList, new TypeToken<List<CategoryModel>>() {}.getType());

        return categoryModelList;
    }
}
