package org.kpiwebdevelopment.imageprocessingservice.model;

import org.kpiwebdevelopment.imageprocessingservice.model.constant.ImageProcessingResult;

import java.util.UUID;

public record ImageProcessingResultMessage(
        UUID postId,
        ImageProcessingResult imageProcessingResult
) {
}
