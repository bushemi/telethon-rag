package com.bushemi.service;

import com.bushemi.model.telethon.TelegramChannelDto;
import com.bushemi.model.telethon.TelegramMessagesDto;

import java.util.List;

public interface TelethonApiService {

    List<TelegramChannelDto> getChannels(int limit, String afterDate);

    TelegramMessagesDto getMessages(Long chatId, Integer limit, int from);

    Integer totalChats();

}
