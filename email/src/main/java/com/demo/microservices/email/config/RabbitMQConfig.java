package com.demo.microservices.email.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {
    @Value("${rabbitmq.email.queue}")
    private String emailVerificationQueue;

    @Value("${rabbitmq.email.exchange}")
    private String exchange;

    @Value("${rabbitmq.email.routing-key}")
    private String routingKey;

    @Bean
    public Queue emailVerificationQueue() {
        return new Queue(emailVerificationQueue, true, false, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange, true, false);
    }

    @Bean
    public Binding binding(Queue emailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(emailQueue)
                .to(topicExchange)
                .with(routingKey);
    }

//    @Bean
//    public MessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(converter());
//        return rabbitTemplate;
//    }
}
