package com.bushemi.model.telethon;

import lombok.Data;

import java.util.List;

@Data
public class TelegramMessages {

    private Long chatId;
    private Integer from;
    private Integer limit;
    private List<TelegramMessage> messages;
    private Integer total;

}
