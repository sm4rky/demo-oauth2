package com.demo.microservices.auth.producer;

import com.demo.microservices.common.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    @Value("${rabbitmq.email.exchange}")
    private String exchange;

    @Value("${rabbitmq.email.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendEmail(EmailRequest request) {
        rabbitTemplate.convertAndSend(exchange, routingKey, request);
    }
}