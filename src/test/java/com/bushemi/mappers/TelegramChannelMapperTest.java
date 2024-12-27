package com.bushemi.mappers;

import com.bushemi.dao.entity.TelegramChannel;
import com.bushemi.model.telethon.TelegramChannelDto;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TelegramChannelMapperTest {

    private final TelegramChannelMapper mapper = TelegramChannelMapper.MAPPER;

    public static void main(String[] args) {
        String dateTimeString = "2024-12-19 18:48:29+00:00";

        // Define a formatter for the input string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxxx");

        // Parse the string to ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString, formatter);

        // Convert to java.util.Date
        Date date = Date.from(zonedDateTime.toInstant());

        // Print the result
        System.out.println("Parsed java.util.Date: " + date);
    }

    @Test
    public void should_map_from_dto() {
        //given
        TelegramChannelDto telegramChannelDto = new TelegramChannelDto();
        telegramChannelDto.setDate("2024-12-19 18:48:29+01:00");
        telegramChannelDto.setId(10L);
        telegramChannelDto.setName("12345_test");
        telegramChannelDto.setIs_channel(true);
        telegramChannelDto.setIs_group(false);
        telegramChannelDto.setWhole_dialog("1234");

        //when
        TelegramChannel result = mapper.mapFromDto(telegramChannelDto);

        //then
        TelegramChannel expected = TelegramChannel.builder()
                                                  .id(10L)
                                                  .name("12345_test")
                                                  .isChannel(true)
                                                  .isGroup(false)
                                                  .wholeDialog("1234")
                                                  .date(Date.from(Instant.ofEpochMilli(1734630509000L)))
                                                  .build();
        assertThat(result).isEqualTo(expected);

    }

}