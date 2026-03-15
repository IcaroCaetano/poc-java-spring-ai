package com.myprojecticaro.poc_java_spring_ai.ollama.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }

    public String ask(String question) {

        return chatClient.prompt()
                .system("""
                        Você responde perguntas usando os documentos fornecidos.
                        Caso não encontre a resposta nos documentos,
                        informe que não possui essa informação.
                        """)
                .user(question)
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .call()
                .content();
    }
}