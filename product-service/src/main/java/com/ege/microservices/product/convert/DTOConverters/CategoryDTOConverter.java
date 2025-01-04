package com.ege.microservices.product.convert.DTOConverters;

import com.ege.microservices.product.entities.CategoryEntity;
import com.ege.microservices.product.services.dtos.CategoryDto;
import com.ege.microservices.product.services.dtos.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDTOConverter {

    private final ModelMapper modelMapper;

    public CategoryEntity convertCategoryRequestDtoToCategoryEntity(CategoryRequestDto categoryRequestDto){

        CategoryEntity categoryEntity = modelMapper.map(categoryRequestDto, CategoryEntity.class);

        return categoryEntity;
    }

    public CategoryDto convertCategoryEntityToCategoryDto(CategoryEntity categoryEntity){

        CategoryDto categoryDto = modelMapper.map(categoryEntity, CategoryDto.class);

        return categoryDto;
    }

    public List<CategoryDto> convertCategoryEntityListToCategoryDtoList(List<CategoryEntity> categoryEntityList){

        List<CategoryDto> categoryDtoList = modelMapper.map(categoryEntityList, new TypeToken<List<CategoryDto>>() {}.getType());

        return categoryDtoList;
    }
}
