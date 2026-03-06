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

        /**
         * Entrada:
         *
         * curl "http://localhost:8080/ai/embedding?text=Spring Boot Framework"
         */

        return embeddingService.generateEmbedding(text);

        /**
         *StatusCode        : 200
         * StatusDescription :
         * Content           : [-0.0016069969,0.023146546,-0.18584801,-0.008045704,0.059643984,0.008316185,-0.024521545,-0.024402346,-0.019876197,-0.049946237,-0.050621
         *                     856,0.06937609,-0.0020750908,0.0012331017,-0.0017515359,-0.0431...
         * RawContent        : HTTP/1.1 200
         *                     Transfer-Encoding: chunked
         *                     Keep-Alive: timeout=60
         *                     Connection: keep-alive
         *                     Content-Type: application/json
         *                     Date: Fri, 06 Mar 2026 01:58:07 GMT
         */
    }
}