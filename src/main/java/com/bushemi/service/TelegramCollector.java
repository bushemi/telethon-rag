package com.bushemi.service;

public interface TelegramCollector {

    void collectChannels();

    void collectMessagesForChannel(Long channelId);

}
