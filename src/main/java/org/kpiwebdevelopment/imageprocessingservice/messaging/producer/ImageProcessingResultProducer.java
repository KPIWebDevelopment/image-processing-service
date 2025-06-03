package org.kpiwebdevelopment.imageprocessingservice.messaging.producer;

import lombok.RequiredArgsConstructor;
import org.kpiwebdevelopment.imageprocessingservice.config.properties.RabbitMQProperties;
import org.kpiwebdevelopment.imageprocessingservice.model.ImageProcessingResultMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageProcessingResultProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    public void sendImageProcessingResultMessage(ImageProcessingResultMessage message) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchange(), rabbitMQProperties.getResultRoutingKey(), message);
    }
}
