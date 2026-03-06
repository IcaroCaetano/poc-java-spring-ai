package com.myprojecticaro.poc_java_spring_ai.ollama.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    /**
     * Embeddings convertem texto em vetores numéricos.
     * @param embeddingModel
     *
     * Exemplo:
     *
     * "Spring Boot"
     * ↓
     * [0.123, -0.882, 0.331, ...]
     */
    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public float[] generateEmbedding(String text) {

        return embeddingModel.embed(text);

    }
}