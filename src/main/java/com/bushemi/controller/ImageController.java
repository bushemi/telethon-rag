package com.bushemi.controller;

import com.bushemi.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("imageFile") MultipartFile file, Model model) {
        log.info("ImageController.uploadImage");
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "imageUpload";
        }

        try {
            imageService.getImageResponse(file);

            // Add success message
            model.addAttribute("message", "Image uploaded successfully!");
            return "imageUpload";
        }
        catch (IOException e) {
            model.addAttribute("message", "An error occurred while uploading the image.");
            return "imageUpload";
        }
    }

    @GetMapping("/imageUpload")
    public String imageUpload() {
        log.info("ImageController.imageUpload");
        return "imageUpload";
    }

}
