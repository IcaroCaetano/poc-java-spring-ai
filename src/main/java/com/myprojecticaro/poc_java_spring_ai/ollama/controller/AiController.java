package com.myprojecticaro.poc_java_spring_ai.ollama.controller;

import com.myprojecticaro.poc_java_spring_ai.ollama.service.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final ChatClient chatClient;
    private final AiService aiService;

    public AiController(ChatClient.Builder builder, AiService aiService) {

        this.chatClient = builder.build();
        this.aiService = aiService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();

        /**
         * Entrada:
         * curl "http://localhost:8080/ai/ask?question=Explique%20o%20que%20%C3%A9%20Spring%20Boot"
         *
         * Saida:
         *
         * StatusCode        : 200
         * StatusDescription :
         * Content           : Spring Boot!
         *
         *                     Spring Boot é um framework de desenvolvimento de software que facilita a criação de aplicativos web e empresariais com Java. Ele é
         *                     baseado no framework Spring, mas oferece uma abordagem ...
         */
    }

    @GetMapping("/askv1")
    public String askv1(@RequestParam String question) {
        return aiService.ask(question);
    }
}