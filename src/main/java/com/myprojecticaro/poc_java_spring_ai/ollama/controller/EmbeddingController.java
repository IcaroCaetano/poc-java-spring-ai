package com.myprojecticaro.poc_java_spring_ai.ollama.controller;

import com.myprojecticaro.poc_java_spring_ai.ollama.service.EmbeddingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("/embedding")
    public float[] embedding(@RequestParam String text) {

        return embeddingService.generateEmbedding(text);

    }
}