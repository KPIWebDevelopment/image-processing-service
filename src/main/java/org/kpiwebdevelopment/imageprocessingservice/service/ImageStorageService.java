package org.kpiwebdevelopment.imageprocessingservice.service;

public interface ImageStorageService {

    void uploadImage(String keyName, byte[] image);
}
