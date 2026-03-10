package com.myprojecticaro.poc_java_spring_ai.ollama.component;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    @Tool(description = "Retorna o clima atual de uma cidade")
    public String getWeather(String city) {

        if(city.equalsIgnoreCase("Fortaleza")) {
            return "Fortaleza está com 29°C e ensolarado.";
        }

        if(city.equalsIgnoreCase("São Paulo")) {
            return "São Paulo está com 22°C e nublado.";
        }

        return "Não tenho dados de clima para essa cidade.";
    }
}