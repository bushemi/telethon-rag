package com.bushemi.dao.repository;

import com.bushemi.dao.entity.TelegramMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TelegramMessageRepository extends JpaRepository<TelegramMessage, Long> {

    boolean existsByTelegramMessageIdAndTelegramChannelId(Long telegramMessageId, Long telegramChannelId);

}
