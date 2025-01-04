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

    private static final long AUDIOBOOKS_CHANNEL_ID = -1002101121969L;
    private final TelegramCollector telegramCollector;
    private final VectorService vectorService;
    private final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(1, 3, 90L, TimeUnit.MINUTES, new LinkedBlockingDeque<>());


    @Scheduled(cron = "0 */10 * * * *")
//    @Scheduled(cron = "0 0 1 * * *")
    public void collectGroups() {
        log.info("Collect groups via cron");
        threadPoolExecutor.submit(() -> {
            log.info("123 from executor");
            telegramCollector.collectChannels();

        });
    }

    @Scheduled(cron = "0 1/10 * * * *")
    public void collects() {
        log.info("collects");
        threadPoolExecutor.submit(() -> {
            log.info("collects_123 from executor");
            telegramCollector.collectMessagesForChannel(AUDIOBOOKS_CHANNEL_ID);
//            vectorCheck();
        });
    }

    private void vectorCheck() {
        long currentTimeMillis = System.currentTimeMillis();
        String format = String.format("There are king and queen in the Great Britain. %s", currentTimeMillis);
        try {
            vectorService.add(format, Map.of("id", 123L, "time", currentTimeMillis));
        }
        catch (Exception e) {
            log.error("Exception", e);
            throw new RuntimeException(e);
        }
    }

}
