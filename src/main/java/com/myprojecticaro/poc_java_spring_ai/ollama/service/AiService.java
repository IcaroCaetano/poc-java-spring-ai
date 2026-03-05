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

    /**
     * Executa uma chamada ao modelo de linguagem utilizando streaming (SSE),
     * permitindo que a resposta seja retornada token por token em tempo real.
     *
     * <p>Este método utiliza o {@link org.springframework.ai.chat.client.ChatClient}
     * com suporte a streaming reativo (Project Reactor), retornando um {@link reactor.core.publisher.Flux}
     * contendo partes incrementais da resposta gerada pelo modelo.</p>
     *
     * <p>A memória conversacional é aplicada através do {@link org.springframework.ai.chat.memory.ChatMemory},
     * associando as mensagens a um {@code conversationId}. Isso permite manter contexto
     * entre múltiplas interações do mesmo usuário.</p>
     *
     *
     * <p><b>Comportamento:</b></p>
     * <ul>
     *     <li>Aplica um System Prompt estruturado</li>
     *     <li>Inclui histórico da conversa via ChatMemory</li>
     *     <li>Retorna resposta incremental via streaming</li>
     * </ul>
     */
    public Flux<String> stream(String conversationId, String question) {
        // Flux<String>
        // Valores chegando ao longo do tempo

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