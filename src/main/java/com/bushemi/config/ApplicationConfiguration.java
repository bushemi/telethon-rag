package com.bushemi.config;

import com.bushemi.scheduler.Scheduler;
import com.bushemi.service.TelegramCollector;
import com.bushemi.service.TelethonApiService;
import com.bushemi.service.VectorService;
import com.bushemi.service.impl.TelethonApiServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootConfiguration
public class ApplicationConfiguration {


    @Bean
    public EmbeddingModel ollamaEmbeddingModel(@Value("${spring.ai.ollama.base-url}") String ollamaUrl) {
        OllamaApi ollamaApi = new OllamaApi(ollamaUrl);
        OllamaOptions defaultOptions = OllamaOptions.builder()
                                                    .withModel("paraphrase-multilingual")
                                                    .build();
        return new OllamaEmbeddingModel(ollamaApi, defaultOptions);
    }

    @Bean
    public VectorStore vectorStore(JdbcTemplate jdbcTemplate,
                                   EmbeddingModel embeddingModel,
                                   @Value("${spring.ai.vectorstore.pgvector.dimensions}") Integer dimensions) {
        return new PgVectorStore(jdbcTemplate, embeddingModel, dimensions);
    }

    @Bean
    public Scheduler botScheduler(TelegramCollector telegramCollector,
                                  VectorService vectorService) {
        return new Scheduler(telegramCollector, vectorService);
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public TelethonApiService telethonApiService(Gson gson,
                                                 @Value("${telethon.service.http-scheme}") String telethonHttpScheme,
                                                 @Value("${telethon.service.host}") String telethonHost,
                                                 @Value("${telethon.service.port}") Integer telethonPort) {
        return new TelethonApiServiceImpl(gson,
                                          telethonHttpScheme,
                                          telethonHost,
                                          telethonPort);
    }

}
