package com.ege.microservices.product.services.dtos.rabbitdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogMessageDTO {

    private String level;

    private String message;

    private String serviceName;

    private long timestamp;
}
