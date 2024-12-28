package com.bushemi.mappers;

import com.bushemi.dao.entity.TelegramChannel;
import com.bushemi.model.telethon.TelegramChannelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Mapper
public interface TelegramChannelMapper {

    TelegramChannelMapper MAPPER = Mappers.getMapper(TelegramChannelMapper.class);

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
    @Mapping(target = "tgChannelId", source = "id")
    @Mapping(target = "isChannel", source = "is_channel")
    @Mapping(target = "isGroup", source = "is_group")
    @Mapping(target = "wholeDialog", source = "whole_dialog")
    @Mapping(target = "date", source = "date", qualifiedByName = "mapDateFromString")
    @Mapping(target = "savingDate", expression = "java(TelegramChannelMapper.getDateNow())")
    TelegramChannel mapFromDto(TelegramChannelDto channel);

}
