package com.ege.microservices.product.services.impl;

import com.ege.microservices.product.services.LogService;
import com.ege.microservices.product.services.dtos.rabbitdtos.LogMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogServiceImpl implements LogService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${sample.rabbitmq.log.exchange}")
    private String logExchange;

    @Value("${sample.rabbitmq.log.routingKey}")
    private String logRoutingKey;

    @Override
    public void sendLog(String level, String message, String serviceName) {
        LogMessageDTO logMessageDTO = new LogMessageDTO(level, message, serviceName, System.currentTimeMillis());
        rabbitTemplate.convertAndSend(logExchange, logRoutingKey, logMessageDTO);
        log.info("Log sent to Logging Service: {}", logMessageDTO);
    }
}
