package com.bushemi.service.impl;

import com.bushemi.dao.entity.TelegramMessage;
import com.bushemi.dao.repository.TelegramChannelRepository;
import com.bushemi.dao.repository.TelegramMessageRepository;
import com.bushemi.mappers.TelegramChannelMapper;
import com.bushemi.mappers.TelegramMessageMapper;
import com.bushemi.model.telethon.TelegramChannelDto;
import com.bushemi.model.telethon.TelegramMessageDto;
import com.bushemi.model.telethon.TelegramMessagesDto;
import com.bushemi.service.TelegramCollector;
import com.bushemi.service.TelethonApiService;
import com.bushemi.service.VectorService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramCollectorImpl implements TelegramCollector {

    private static final int CHAT_CHUNK_SIZE = 50;
    private static final int MESSAGE_CHUNK_SIZE = 100;

    private final TelethonApiService telethonApiService;
    private final TelegramChannelRepository telegramChannelRepository;
    private final TelegramMessageRepository telegramMessageRepository;
    private final VectorService vectorService;

    public static void main(String[] args) {
        String date = "2024-12-24 02:19:35+00:00";
//        Date date1 = mapDateFromString(date);
//        date1.setTime(date1.getTime() + 1000);
//        System.out.println("date1 = " + date1);
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
        System.out.println("date = " + date);
        String datePlusSecond = getDatePlusSecond(date);
        System.out.println("datePlusSecond = " + datePlusSecond);
    }

    private static String getDatePlusSecond(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxxx");

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString, formatter);

        Date date = Date.from(zonedDateTime.toInstant());
        date.setTime(date.getTime() + 1000);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    @Override
    public void collectChannels() {
        int counter = 0;
        Integer totalChats = telethonApiService.totalChats();
        log.info("totalChats = " + totalChats);
        String lastDate = null;
        List<TelegramChannelDto> channels = telethonApiService.getChannels(CHAT_CHUNK_SIZE, null);
        log.info("channels = " + channels.size());
        while (channels.size() == CHAT_CHUNK_SIZE) {
            channels = telethonApiService.getChannels(CHAT_CHUNK_SIZE, lastDate);

//            channels
//                    .forEach(channel -> log.info("tg_chnl_id = {}, date = {}", channel.getId(), channel.getDate()));

            channels
                    .stream()
                    .filter(channel -> !telegramChannelRepository.existsByTgChannelId(channel.getId()))
                    .map(TelegramChannelMapper.MAPPER::mapFromDto)
                    .map(telegramChannelRepository::save)
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

    @Override
    public void collectMessagesForChannel(Long channelId) {
        messages(channelId);

    }

    private void messages(Long channelId) {
        int counter = 0;
        String lastDate = null;
//        do {
        TelegramMessagesDto messages = telethonApiService.getMessages(channelId, MESSAGE_CHUNK_SIZE, null);
        log.info("messages.getMessages().size() = " + messages.getMessages().size());
        while (messages.getMessages().size() == MESSAGE_CHUNK_SIZE) {
            messages = telethonApiService.getMessages(channelId, MESSAGE_CHUNK_SIZE, lastDate);
            log.info("messages.getMessages().size() = " + messages.getMessages().size());
            long savedMessages = messages.getMessages()
                                         .stream()
                                         .filter(msg -> StringUtils.isNotEmpty(msg.getMessage()))
                                         .map(msg -> TelegramMessageMapper.MAPPER.mapFromDto(msg, channelId))
                                         .filter(msg -> !telegramMessageRepository.existsByTelegramMessageIdAndTelegramChannelId(msg.getTelegramMessageId(),
                                                                                                                                 msg.getTelegramChannelId()))
                                         .map(telegramMessageRepository::save)
                                         .peek(this::saveVector)
                                         .count();
//                    .forEach(this::saveVector);
//                    .forEach(msg -> log.info("save msg tg_id = {}, date = {}", msg.getTelegramMessageId(), msg.getDate()));

            TelegramMessageDto telegramMessageDto = messages.getMessages().get(messages.getMessages().size() - 1);
            lastDate = telegramMessageDto.getDate();
            if (lastDate.contains("+")) {
                lastDate = lastDate.substring(0, lastDate.indexOf("+"));
            }
            counter += messages.getMessages().size();
            log.info("Processed [{}] of [{}] total messages ", counter, messages.getTotal());
            if (savedMessages == 0) {
                log.info("All existing messages were saved before");
                return;
            }

        }
//        while (true);

    }

    private void saveVector(TelegramMessage telegramMessage) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("id", telegramMessage.getId());
        metadata.put("tg_msg_id", telegramMessage.getTelegramMessageId());
        metadata.put("tg_channel_id", telegramMessage.getTelegramChannelId());
        vectorService.add(telegramMessage.getMessage(), metadata);
    }

}
