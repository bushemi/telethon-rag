package com.bushemi.service.impl;

import com.bushemi.dao.repository.TelegramChannelRepository;
import com.bushemi.mappers.TelegramChannelMapper;
import com.bushemi.model.telethon.TelegramChannelDto;
import com.bushemi.service.TelegramCollector;
import com.bushemi.service.TelethonApiService;
import com.bushemi.service.VectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramCollectorImpl implements TelegramCollector {

    private static final int CHAT_CHUNK_SIZE = 10;
    private final TelethonApiService telethonApiService;
    private final TelegramChannelRepository telegramChannelRepository;
    private final VectorService vectorService;

    @Override
    public void collectChannels() {
        int counter = 0;
        Integer totalChats = telethonApiService.totalChats();
        log.info("totalChats = " + totalChats);
        String lastDate = null;
        List<TelegramChannelDto> channels = telethonApiService.getChannels(10, null);
        log.info("channels = " + channels);
        while (counter <= totalChats) {
            channels = telethonApiService.getChannels(CHAT_CHUNK_SIZE, lastDate);
            System.out.println("channels1 = " + channels);

            channels
                    .forEach(channel -> log.info("tg_id = {}, date = {}", channel.getId(), channel.getDate()));

            channels
                    .stream()
                    .filter(channel -> !telegramChannelRepository.existsByTgChannelId(channel.getId()))
                    .map(TelegramChannelMapper.MAPPER::mapFromDto)
                    .map(telegramChannelRepository::save)
//                    .peek(channel ->)
                    .forEach(channel -> log.info("saved new channel with Id = [{}] and name = [{}]",
                                                 channel.getTgChannelId(),
                                                 channel.getName()));

            TelegramChannelDto telegramChannelDto = channels.get(channels.size() - 1);
            lastDate = telegramChannelDto.getDate();
            if (lastDate.contains("+")) {
                lastDate = lastDate.substring(0, lastDate.indexOf("+"));
            }
            counter += channels.size();
            log.info("processed = {}", counter);
        }

    }

//    public void collectMessagesForChannel(Long channelId) {
//        telethonApiService.getMessages(channelId, 10, 0);
//    }

}
