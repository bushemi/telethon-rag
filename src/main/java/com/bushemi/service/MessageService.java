package com.bushemi.service;

import com.bushemi.model.Message;

public interface MessageService {

    Message getMessage(String inputText);

    void addNewMessage(String inputText);

}
