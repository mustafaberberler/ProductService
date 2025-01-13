package com.ege.microservices.product.utils.rabbitutils;


import com.ege.microservices.product.services.ProductService;
import com.ege.microservices.product.services.dtos.rabbitdtos.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMessageListener {

    private final ProductService productService;

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void consumeMessageFromQueue(OrderMessage orderMessage){
      log.info("Received the message: {}", orderMessage.toString());

      switch (orderMessage.getAction()){
          case "decrease_stock":
              productService.decreaseStock(orderMessage.getProducts());
              break;

          case "increase_stock":
              productService.increaseStock(orderMessage.getProducts());
              break;

          default:
              throw new IllegalArgumentException("Unknown action: " + orderMessage.getAction());
      }


    }

}
