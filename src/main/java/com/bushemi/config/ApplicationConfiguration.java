package com.bushemi.config;

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

}
