package com.myprojecticaro.poc_java_spring_ai.ollama.domain;

public record WeatherResponse(
        String city,
        int temperature,
        String condition
) {}