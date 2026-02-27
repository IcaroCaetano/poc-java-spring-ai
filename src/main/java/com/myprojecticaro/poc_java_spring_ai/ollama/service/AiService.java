package com.myprojecticaro.poc_java_spring_ai.ollama.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder builder) {

        this.chatClient = builder.build();
    }

    public String ask(String question) {

        return chatClient.prompt()
                .system("""
                        Você é um especialista em Java e Spring.
                        Responda de forma técnica, clara e objetiva.
                        """)
                .user(question)
                .call()
                .content();
    }
}