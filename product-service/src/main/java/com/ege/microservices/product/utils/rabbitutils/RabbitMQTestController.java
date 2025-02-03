package com.ege.microservices.product.utils.rabbitutils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RabbitMQTestController {


//    private final RabbitMQProducer producer;
//
//    @GetMapping("/test-rabbitmq")
//    public String testRabbitMQ(@RequestParam(defaultValue = "Test mesajı") String message) {
//        return producer.sendTestMessage(message);
//    }
}
