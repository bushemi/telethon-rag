package com.bushemi.model.telethon;

import lombok.Data;

@Data
public class TelegramChannelDto {

    private Long id;
    private String date;
    private Boolean is_channel;
    private Boolean is_group;
    private String name;
    private String whole_dialog;

}
