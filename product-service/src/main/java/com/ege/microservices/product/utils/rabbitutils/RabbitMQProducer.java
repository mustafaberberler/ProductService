package com.ege.microservices.product.utils.rabbitutils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    public String sendTestMessage(String message) {
//        try {
//            rabbitTemplate.convertAndSend(RabbitMqConfig.QUEUE_NAME, message);
//            return "Mesaj başarıyla gönderildi: " + message;
//        } catch (Exception e) {
//            return "Bağlantı hatası: " + e.getMessage();
//        }
//    }

}
