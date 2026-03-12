package com.myprojecticaro.poc_java_spring_ai.ollama.component;

import org.springframework.stereotype.Component;

@Component
public class InputGuardrail {

    public void validate(String question) {

        if(question == null || question.isBlank()) {
            throw new IllegalArgumentException("Pergunta inválida.");
        }

        String lower = question.toLowerCase();

        if(lower.contains("hack") ||
           lower.contains("invadir") ||
           lower.contains("ataque")) {

            throw new IllegalArgumentException(
                    "Pergunta bloqueada por política de segurança."
            );
        }
    }
}