package com.bushemi.model.telethon;

import lombok.Data;

@Data
public class TelegramMessage {

    private Long id;
    private String date;
    private String message;
    private Long sender_id;
    private String whole_msg;

}
