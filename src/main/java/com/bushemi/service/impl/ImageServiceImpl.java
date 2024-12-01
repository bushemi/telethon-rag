package com.bushemi.service.impl;

import com.bushemi.service.ImageService;
import com.bushemi.service.LLMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final LLMService llmService;

    @Override
    public String logImageByteLength(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        long byteLength = bytes.length;
        System.out.println("Uploaded image byte length: " + byteLength);
        String textFromLlmByImage = llmService.getTextFromLlmByImage("Explain what you see on this image. Add context if it is needed",
                                                                     encodedString);
        log.info(textFromLlmByImage);
        return textFromLlmByImage;
    }

}
