package com.bushemi.scheduler;

import com.bushemi.service.TelegramCollector;
import com.bushemi.service.VectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
//@Service
@RequiredArgsConstructor
public class Scheduler {

    private final TelegramCollector telegramCollector;
    private final Long telegramChannelId;
    private final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(1, 3, 90L, TimeUnit.MINUTES, new LinkedBlockingDeque<>());


    @Scheduled(cron = "0 */10 * * * *")
    public void collectGroups() {
        log.info("Process groups via cron");
        threadPoolExecutor.submit(() -> {
            log.info("Process telegram channels");
            telegramCollector.collectChannels();

        });
    }

    @Scheduled(cron = "0 1/10 * * * *")
    public void collects() {
        log.info("Process messages via cron");
        threadPoolExecutor.submit(() -> {
            log.info("Process telegram messages");
            telegramCollector.collectMessagesForChannel(telegramChannelId);
        });
    }

}
