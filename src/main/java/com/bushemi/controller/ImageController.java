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
            // Log the byte length of the file
            imageService.logImageByteLength(file);

            // Add success message
            model.addAttribute("message", "Image uploaded successfully!");
            return "imageUpload";
        }
        catch (IOException e) {
            model.addAttribute("message", "An error occurred while uploading the image.");
            return "imageUpload";
        }
    }
//    The character's design is reminiscent of animated films and series known for their stylized characters and exaggerated expressions. Without additional context or information about the character or scene, it's difficult to determine the specific source material, the story behind the image, or the intended message conveyed through the character's expression and surroundings.

//    The image shows a professional soccer player in action during a game. He is wearing the uniform of the Los Angeles Galaxy, which includes a white jersey with blue accents and the sponsor "Herbalife" on the front. The jersey also features the team's logo. On his right leg, he has a noticeable tattoo, and he's wearing white socks with black details, along with soccer cleats. He is in mid-motion, either kicking or preparing to kick the ball, which is rolling towards him on the field.
//    The player appears focused and is looking down at the ball, likely planning his next move or assessing the best angle for a pass or shot. The background is a typical soccer stadium with seating that suggests it's a professional venue, though no spectators are visible in this particular frame of the image. There's also a partial view of an advertisement board on the side of the pitch, which is a common feature in most soccer stadiums.
//    Without more context or information about the event (such as the score, the opponents, or the specific match), it's difficult to provide additional details. However, based on the image alone, we can say that the player is actively engaged in a competitive game of soccer and is part of the Los Angeles Galaxy team.

    @GetMapping("/imageUpload")
    public String imageUpload() {
        log.info("ImageController.imageUpload");
        return "imageUpload";
    }

}
