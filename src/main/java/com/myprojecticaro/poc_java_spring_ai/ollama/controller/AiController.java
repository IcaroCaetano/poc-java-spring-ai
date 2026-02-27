package com.myprojecticaro.poc_java_spring_ai.ollama.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient.Builder builder) {

        this.chatClient = builder.build();
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}