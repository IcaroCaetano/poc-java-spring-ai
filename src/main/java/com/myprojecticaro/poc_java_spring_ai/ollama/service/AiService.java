package com.myprojecticaro.poc_java_spring_ai.ollama.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiService {

    private final ChatClient chatClient;

    private final ChatMemory chatMemory;


    public AiService(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder.build();
        this.chatMemory = chatMemory;
    }

    public String ask(String question) {

        return chatClient.prompt()
                .system("""
                        Você é um especialista em Java e Spring.
                        Responda de forma técnica, clara e objetiva.
                         Se possível, inclua exemplos práticos.
                        """)
                .user(question)
                .call()
                .content();
    }

    public String askv2(String conversationId, String question) {

        return chatClient.prompt()
                .system("""
                    Você é um especialista em Java e Spring.
                    Use o contexto da conversa quando necessário.
                    """)
                .user(question)
                .advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .conversationId(conversationId)
                                .build()
                )
                .call()
                .content();
    }

    public Flux<String> stream(String conversationId, String question) {

        return chatClient.prompt()
                .system("""
                    Você é um especialista em Java e Spring.
                    Responda de forma clara e use o contexto da conversa.
                    """)
                .user(question)
                .advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .conversationId(conversationId)
                                .build()
                )
                .stream()
                .content();
    }
}