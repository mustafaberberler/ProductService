package com.ege.microservices.product.services.dtos;

import com.ege.microservices.product.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productId;

    private String productName;

    private String description;

    private Integer quantity;

    private BigDecimal price;
    private CategoryEntity category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
