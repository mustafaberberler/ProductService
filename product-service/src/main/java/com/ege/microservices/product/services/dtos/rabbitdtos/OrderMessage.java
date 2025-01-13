package com.ege.microservices.product.services.dtos.rabbitdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    private String action;
    private List<ProductRabbitDTO> products;

}
