# POC Java Spring AI

## 🤖 POC — Spring AI com Ollama (Chat Simples)

### 📌 1️⃣ Objetivo

Esta Proof of Concept (POC) demonstra a integração entre:

- Spring AI

- Spring Boot

- Ollama

- Java 21

O objetivo da Fase 1 é:

- Integrar uma LLM local

- Criar um endpoint REST

- Enviar prompts para o modelo

- Retornar resposta via API

### 🏗️ 2️⃣ Stack Tecnológica

### 🧠 3️⃣ Arquitetura Atual (Fase 1)

````
controller
 └── AiController
````

Fluxo:

````
Client → Controller → ChatClient → Ollama (localhost:11434) → Response
````
### ⚙️ 4️⃣ Configuração

application.yml

````
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        model: llama3
        options:
          temperature: 0.3
````

### 🚀 5️⃣ Execução

#### 1️⃣ Subir Ollama

````
ollama run llama3
````

#### 2️⃣ Rodar aplicação

````
mvn spring-boot:run
````

#### 3️⃣ Testar endpoint

````
 curl "http://localhost:8080/ai/ask?question=Explique%20o%20que%20%C3%A9%20Spring%20Boot"
````
