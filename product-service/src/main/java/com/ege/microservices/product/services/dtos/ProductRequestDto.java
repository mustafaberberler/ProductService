package com.ege.microservices.product.services.dtos;

import com.ege.microservices.product.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String productName;

    private String description;

    private Integer quantity;

    private BigDecimal price;
    private CategoryEntity category;
}
