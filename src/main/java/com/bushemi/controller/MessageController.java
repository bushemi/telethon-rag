package com.bushemi.controller;

import com.bushemi.model.Message;
import com.bushemi.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/message")
    public String getMessage(@RequestParam(required = false) String text, Model model) {
        Message message = messageService.getMessage(text);
        model.addAttribute("message", message);
        log.info("MessageController.getMessage");
        return "getMessage";
    }

}
