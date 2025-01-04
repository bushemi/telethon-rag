package com.bushemi.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity(name = "TELEGRAM_MESSAGE")
@Table
@EqualsAndHashCode
public class TelegramMessage {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "TELEGRAM_MESSAGE_ID")
    private Long telegramMessageId;
    @Column(name = "TELEGRAM_CHANNEL_ID")
    private Long telegramChannelId;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "MESSAGE", columnDefinition = "VARCHAR")
    private String message;
    @Column(name = "SENDER_ID")
    private Long senderId;
    @Column(name = "WHOLE_MESSAGE", columnDefinition = "VARCHAR")
    private String wholeMsg;
    /**
     * date of saving to DB
     */
    @Column(name = "SAVING_DATE")
    private Date savingDate;

}
