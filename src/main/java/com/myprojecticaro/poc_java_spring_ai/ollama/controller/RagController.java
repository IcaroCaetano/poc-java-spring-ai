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
        return ragService.ask(question);
    }

}