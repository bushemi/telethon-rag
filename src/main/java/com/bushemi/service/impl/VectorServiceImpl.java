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
        System.out.println("VectorServiceImpl.find");
        System.out.println("text = " + text);

//#################### many documents
//        List<Document> documents = List.of(
//                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!",
//                             Map.of("meta1", "meta1")),
//                new Document("The World is Big and Salvation Lurks Around the Corner"),
//                new Document("There are king and queen in the Great Britain.",
//                             Map.of("meta2", "meta2")));

//###################### one document
        //        List<Document> documents = List.of(
        //                new Document("There are king and queen in the Great Britain.",
//                             Map.of("meta2", "meta2"))
//        );

        log.info("Created documents");
// Add the documents to PGVector
//        vectorStore.add(documents);
        log.info("Added documents");

// Retrieve documents similar to a query
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

    }

}

