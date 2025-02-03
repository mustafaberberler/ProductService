package com.ege.microservices.product.utils.rabbitutils;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

//    @Value("${sample.rabbitmq.exchange}")
//    private String exchange;
//
//    @Value("${sample.rabbitmq.queue}")
//    private String queueName;
//
//    @Value("${sample.rabbitmq.routingKey}")
//    private String routingKey;
//
//    @Bean
//    DirectExchange exchange(){
//        return new DirectExchange(exchange);
//    }
//
//    @Value("${sample.rabbitmq.log.queue}")
//    private String logQueueName;
//
//    @Value("${sample.rabbitmq.log.exchange}")
//    private String logExchange;
//
//    @Value("${sample.rabbitmq.log.routingKey}")
//    private String logRoutingKey;
//
//    @Bean
//    Queue firstStepQueue(){
//        return new Queue(queueName, false);
//    }
//
//    @Bean
//    Binding binding(Queue firstStepQueue, DirectExchange exchange){
//        return BindingBuilder.bind(firstStepQueue).to(exchange).with(routingKey);
//    }
//
//    // Logging için Exchange ve Queue
//    @Bean
//    DirectExchange logExchange() {
//        return new DirectExchange(logExchange);
//    }
//
//    @Bean
//    Queue logQueue() {
//        return new Queue(logQueueName, true);
//    }
//
//    @Bean
//    Binding logBinding(Queue logQueue, DirectExchange logExchange) {
//        return BindingBuilder.bind(logQueue).to(logExchange).with(logRoutingKey);
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
//
////    @Bean
////    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
////        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
////        factory.setConnectionFactory(connectionFactory);
////        factory.setMessageConverter(jsonMessageConverter());
////        return factory;
////    }
//
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
//        return new SimpleMessageListenerContainer(connectionFactory);
//    }



    /// TEST QUEUE

//public static final String QUEUE_NAME = "testQueue";
//
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE_NAME, false);
//    }

    ///  TEST QUEUE END


    /// 03.02.2025

    public static final String logQueueName = "log_service_queue";

    public static final String productQueueName = "product_service_queue";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        return new SimpleMessageListenerContainer(connectionFactory);
    }

    @Bean
    public Queue logQueue() {
        return new Queue(logQueueName, true); // durable=true
    }

    @Bean
    public Queue productQueue(){
        return new Queue(productQueueName, true);
    }


}
