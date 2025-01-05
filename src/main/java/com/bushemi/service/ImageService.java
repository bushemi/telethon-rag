package com.bushemi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String getImageResponse(MultipartFile file) throws IOException;

}
