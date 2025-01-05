package com.bushemi.service.impl;

import com.bushemi.service.VectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VectorServiceImpl implements VectorService {

    private final VectorStore vectorStore;

    @Override
    public String find(String text) {

        List<Document> results = vectorStore.similaritySearch(SearchRequest.query(text)
                                                                           .withTopK(2));

        log.info("Searched for results");

        results.stream()
               .map(Document::getContent)
               .forEach(log::info);

        return results.stream()
                      .findFirst()
                      .map(Document::getContent)
                      .orElse("");

    }

    @Override
    public void add(String text) {
        log.error("Adding new text is not implemented yet");
    }

}

