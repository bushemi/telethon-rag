package com.bushemi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String logImageByteLength(MultipartFile file) throws IOException;

}
