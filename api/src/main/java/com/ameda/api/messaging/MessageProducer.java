package com.ameda.api.messaging;


import com.ameda.shared.messagingConfig.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Author: kev.Ameda
 */
@Service
public class MessageProducer {
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public <T> void sendMessage(T payload) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                payload);

    }
}
