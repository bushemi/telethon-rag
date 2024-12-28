package com.bushemi.dao.repository;

import com.bushemi.dao.entity.TelegramChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TelegramChannelRepository extends JpaRepository<TelegramChannel, Long> {

    boolean existsByTgChannelId(Long ttgChannelId);

}
