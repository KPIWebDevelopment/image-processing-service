package org.kpiwebdevelopment.imageprocessingservice.model;

import java.util.UUID;

public record ImageProcessingRequestMessage(
        UUID postId,
        byte[] image
) {
}
