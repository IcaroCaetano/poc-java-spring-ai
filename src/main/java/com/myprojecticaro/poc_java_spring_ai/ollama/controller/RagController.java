package com.myprojecticaro.poc_java_spring_ai.ollama.controller;

import com.myprojecticaro.poc_java_spring_ai.ollama.service.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {

        /**
         * Resumo:
         * Pergunta do usuário
         *         │
         *         ▼
         * Busca semântica nos embeddings
         *         │
         *         ▼
         * Recupera documentos relevantes
         *         │
         *         ▼
         * Envia pergunta + contexto para o LLM
         *         │
         *         ▼
         * Resposta baseada no contexto
         *
         * Entrada:
         *
         * http://localhost:8080/rag/ask?question=O que é Spring Boot?
         */
        return ragService.ask(question);

        /**
         * SAida:
         *
         * SpringBoot é um framework Java para criação de aplicações standalone. Ele
         * facilita a criação de microservices.
         */
    }

}