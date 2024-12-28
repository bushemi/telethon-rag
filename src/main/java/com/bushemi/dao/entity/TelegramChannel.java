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
@Entity(name = "TELEGRAM_CHANNEL")
@Table
@EqualsAndHashCode
public class TelegramChannel {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "TELEGRAM_CHANNEL_ID")
    private Long tgChannelId;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "IS_CHANNEL")
    private Boolean isChannel;
    @Column(name = "IS_GROUP")
    private Boolean isGroup;
    @Column(name = "NAME")
    private String name;
    @Column(name = "WHOLE_DIALOG", columnDefinition = "VARCHAR")
    private String wholeDialog;
    /**
     * date of saving to DB
     */
    @Column(name = "SAVING_DATE")
    private Date savingDate;

}
