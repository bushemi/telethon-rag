package com.bushemi.model.telethon;

import lombok.Data;

import java.util.List;

@Data
public class TelegramMessagesDto {

    private Long chatId;
    private Integer from;
    private Integer limit;
    private List<TelegramMessageDto> messages;
    private Integer total;

}
