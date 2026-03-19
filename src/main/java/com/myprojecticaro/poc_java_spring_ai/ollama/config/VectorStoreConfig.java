package com.myprojecticaro.poc_java_spring_ai.ollama.config;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Bean
    public VectorStore vectorStore(PgVectorStore pgVectorStore) {
        return pgVectorStore;
    }
}