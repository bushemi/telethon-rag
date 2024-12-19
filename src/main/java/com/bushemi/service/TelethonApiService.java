package com.bushemi.service;

import com.bushemi.model.telethon.TelegramChannel;
import com.bushemi.model.telethon.TelegramMessages;

import java.util.List;

public interface TelethonApiService {

    List<TelegramChannel> getChannels(int limit);

    TelegramMessages getMessages(Long chatId, Integer limit, int from);

    Integer totalChats();
}
