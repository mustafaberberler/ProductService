package com.ege.microservices.product.services.dtos.rabbitdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRabbitDTO {

    private String productId;

    private int quantity;
}
