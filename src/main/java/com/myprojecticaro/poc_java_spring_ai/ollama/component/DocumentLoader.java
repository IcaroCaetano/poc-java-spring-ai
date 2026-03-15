package com.myprojecticaro.poc_java_spring_ai.ollama.component;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentLoader {

    private final VectorStore vectorStore;

    public DocumentLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadDocuments() {

        List<Document> documents = List.of(
                new Document("Spring Boot é um framework Java para criação de aplicações standalone."),
                new Document("Spring Boot facilita a criação de microservices."),
                new Document("Spring AI permite integrar modelos de linguagem em aplicações Spring."),
                new Document("Embeddings convertem texto em vetores numéricos.")
        );

        vectorStore.add(documents);
    }
}