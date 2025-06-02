package org.kpiwebdevelopment.imageprocessingservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.kpiwebdevelopment.imageprocessingservice.exception.ImageProcessingException;
import org.kpiwebdevelopment.imageprocessingservice.service.ImageProcessingService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class ImageProcessingServiceImpl implements ImageProcessingService {

    @Override
    public byte[] compressImage(byte[] image) throws ImageProcessingException {
        try (var inputStream = new ByteArrayInputStream(image);
             var outputStream = new ByteArrayOutputStream()) {
            Thumbnails.of(inputStream)
                    .size(800, 600)
                    .outputQuality(0.8)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new ImageProcessingException("Error while compressing image", e);
        }
    }
}
