package org.kpiwebdevelopment.imageprocessingservice.messaging.consumer;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kpiwebdevelopment.imageprocessingservice.exception.ImageProcessingException;
import org.kpiwebdevelopment.imageprocessingservice.messaging.producer.ImageProcessingResultProducer;
import org.kpiwebdevelopment.imageprocessingservice.model.ImageProcessingRequestMessage;
import org.kpiwebdevelopment.imageprocessingservice.model.ImageProcessingResultMessage;
import org.kpiwebdevelopment.imageprocessingservice.model.constant.ImageProcessingResult;
import org.kpiwebdevelopment.imageprocessingservice.service.ImageProcessingService;
import org.kpiwebdevelopment.imageprocessingservice.service.ImageStorageService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageProcessingRequestConsumer {

    private static final String POST_IMAGE_KEY_FORMAT = "post-images/%s";

    private final ImageProcessingService imageProcessingService;
    private final ImageProcessingResultProducer imageProcessingResultProducer;
    private final ImageStorageService imageStorageService;

    @RabbitListener(
            queues = "${image-processing.rabbitmq.request-queue}",
            messageConverter = "jackson2JsonMessageConverter"
    )
    public void handleImageProcessingRequestMessage(
            ImageProcessingRequestMessage message,
            Channel channel,
            Message amqpMessage
    ) throws IOException {
        log.info("Received image processing request message: {}", message);
        var deliveryTag = amqpMessage.getMessageProperties().getDeliveryTag();
        var imageProcessingResult = ImageProcessingResult.SUCCESS;
        Optional<byte[]> compressedImage = Optional.empty();
        try {
            compressedImage = Optional.of(imageProcessingService.compressImage(message.image()));
        } catch (ImageProcessingException e) {
            log.error("Error while compressing image", e);
            imageProcessingResult = ImageProcessingResult.FAILED;
        }
        if (imageProcessingResult == ImageProcessingResult.SUCCESS) {
            imageStorageService.uploadImage(
                    String.format(POST_IMAGE_KEY_FORMAT, message.postId()),
                    compressedImage.get()
            );
        }
        var resultMessage = new ImageProcessingResultMessage(message.postId(), imageProcessingResult);
        imageProcessingResultProducer.sendImageProcessingResultMessage(resultMessage);
        channel.basicAck(deliveryTag, false);
    }
}
