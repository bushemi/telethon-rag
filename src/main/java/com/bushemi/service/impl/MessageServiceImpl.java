package com.bushemi.service.impl;

import com.bushemi.model.Message;
import com.bushemi.service.MessageService;
import com.bushemi.service.VectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final VectorService vectorService;

    @Override
    public Message getMessage(String inputText) {
        return Optional.ofNullable(inputText)
                       .map(vectorService::find)
                       .map(Message::new)
                       .orElse(new Message(inputText));
    }

    @Override
    public void addNewMessage(String inputText) {
        vectorService.add(inputText);
    }

}
