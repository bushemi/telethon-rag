package com.bushemi.mappers;

import com.bushemi.dao.entity.TelegramChannel;
import com.bushemi.model.telethon.TelegramChannelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Mapper
public interface TelegramChannelMapper {

    TelegramChannelMapper MAPPER = Mappers.getMapper(TelegramChannelMapper.class);

    @Named("mapDateFromString")
    static Date mapDateFromString(String dateTimeString) {
        // Define a formatter for the input string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxxx");

        // Parse the string to ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString, formatter);

        // Convert to java.util.Date
        return Date.from(zonedDateTime.toInstant());
    }


    //    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "isChannel", source = "is_channel")
    @Mapping(target = "isGroup", source = "is_group")
    @Mapping(target = "wholeDialog", source = "whole_dialog")
    @Mapping(target = "date", source = "date", qualifiedByName = "mapDateFromString")
    TelegramChannel mapFromDto(TelegramChannelDto channel);

}
