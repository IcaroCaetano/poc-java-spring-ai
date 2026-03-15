package com.myprojecticaro.poc_java_spring_ai.ollama.component;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class MathTool {

    @Tool(description = """
            Realiza cálculos matemáticos.
            Use quando o usuário pedir operações como soma,
            multiplicação ou divisão.
            """)
    public String calculate(String expression) {

        try {
            String[] parts;

            if(expression.contains("*")) {
                parts = expression.split("\\*");
                int result = Integer.parseInt(parts[0].trim()) *
                             Integer.parseInt(parts[1].trim());
                return "Resultado: " + result;
            }

            if(expression.contains("+")) {
                parts = expression.split("\\+");
                int result = Integer.parseInt(parts[0].trim()) +
                             Integer.parseInt(parts[1].trim());
                return "Resultado: " + result;
            }

        } catch (Exception e) {
            return "Erro ao calcular expressão.";
        }

        return "Operação não suportada.";
    }
}