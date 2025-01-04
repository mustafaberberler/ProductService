package com.ege.microservices.product.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {

    private String categoryId;

    private String categoryName;
}
