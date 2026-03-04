package com.myprojecticaro.poc_java_spring_ai.ollama.controller;

import com.myprojecticaro.poc_java_spring_ai.ollama.domain.AnswerResponse;
import com.myprojecticaro.poc_java_spring_ai.ollama.domain.AskRequest;
import com.myprojecticaro.poc_java_spring_ai.ollama.domain.QuestionRequest;
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

        /**
         * Entrada:
         * curl "http://localhost:8080/ai/askv1?question=Explique%20o%20que%20%C3%A9%20Spring%20Boot"
         */

        return aiService.ask(question);


        /**
         * Saida:
         *
         *
         StatusCode        : 200
         StatusDescription :
         Content           : Spring Boot!

         Spring Boot is a sub-project of the Spring Framework that simplifies the creation of stand-alone, production-grade applications with
         minimal configuration and coding effort. It's designe...
         RawContent        : HTTP/1.1 200
         Content-Length: 2555
         Content-Type: text/plain;charset=UTF-8
         Date: Tue, 03 Mar 2026 02:06:58 GMT
         */
    }

    @PostMapping("/ask")
    public AnswerResponse ask(@RequestBody QuestionRequest request) {

        String response = aiService.ask(request.question());

        return new AnswerResponse(response);
    }
}