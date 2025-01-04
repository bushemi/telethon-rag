package com.bushemi.mappers;

import com.bushemi.dao.entity.TelegramMessage;
import com.bushemi.model.telethon.TelegramMessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Mapper
public interface TelegramMessageMapper {

    TelegramMessageMapper MAPPER = Mappers.getMapper(TelegramMessageMapper.class);

    @Named("mapDateFromString")
    static Date mapDateFromString(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxxx");

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString, formatter);

        return Date.from(zonedDateTime.toInstant());
    }

    static Date getDateNow() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(Clock.systemUTC());
        return Date.from(zonedDateTime.toInstant());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "telegramMessageId", source = "telegramMessageDto.id")
    @Mapping(target = "telegramChannelId", source = "channelId")
    @Mapping(target = "date", source = "telegramMessageDto.date", qualifiedByName = "mapDateFromString")
    @Mapping(target = "message", source = "telegramMessageDto.message")
    @Mapping(target = "senderId", source = "telegramMessageDto.sender_id")
    @Mapping(target = "wholeMsg", source = "telegramMessageDto.whole_msg")
    @Mapping(target = "savingDate", expression = "java(TelegramMessageMapper.getDateNow())")
    TelegramMessage mapFromDto(TelegramMessageDto telegramMessageDto, Long channelId);

}
