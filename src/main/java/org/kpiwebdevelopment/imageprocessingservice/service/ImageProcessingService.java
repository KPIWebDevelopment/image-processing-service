package org.kpiwebdevelopment.imageprocessingservice.service;

import org.kpiwebdevelopment.imageprocessingservice.exception.ImageProcessingException;

public interface ImageProcessingService {

    byte[] compressImage(byte[] image) throws ImageProcessingException;
}
