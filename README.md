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

| Tecnologia   | Versão          |
|--------------|-----------------|
| Java         | 21 (LTS)        |
| Spring Boot  | 3.3+            |
| Spring AI    | 1.0.0           |
| Maven        | 3.9+            |
| Ollama       | Última versão   |
| Modelo       | llama3          |

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
### 🔍 6️⃣ Endpoint disponível

````
GET /ai/ask?question={pergunta}
````

Exemplo:

````
GET /ai/ask?question=Explique%20Java%20Locks
````


### 🚀 Fase 2 — Prompt Template Estruturado

#### 🎯 Objetivo

- Separar System Prompt e User Prompt

- Definir comportamento do modelo

- Melhorar qualidade e consistência das respostas

- Introduzir camada de serviço

#### 🏗️ Arquitetura Atual (Fase 2)

controller
 └── AiController

service
 └── AiService

Fluxo atualizado:

````
Client → Controller → Service → ChatClient → Ollama → Response
````

### 🧠 System Prompt Implementado

O modelo agora recebe contexto fixo:

Você é um especialista em Java e Spring.
Responda de forma técnica, clara e objetiva.

Isso garante:

- Respostas mais técnicas

- Padronização

- Controle de tom

- Maior previsibilidade


## ⚙️ 4️⃣ Configuração

```
application.yml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        model: llama3
        options:
          temperature: 0.3
```

### 🚀 5️⃣ Execução

#### 1️⃣ Subir Ollama

```
ollama run llama3
```

#### 2️⃣ Rodar aplicação

```
mvn spring-boot:run
```


#### 3️⃣ Testar endpoint

````
 curl "http://localhost:8080/ai/ask?question=Explique%20o%20que%20%C3%A9%20Spring%20Boot"
````

### 🔍 6️⃣ Endpoint disponível

```
GET /ai/ask?question={pergunta}
```

Exemplo:

```
GET /ai/ask?question=Explique%20Java%20Locks
```

### 🧪 Observações Técnicas

- A primeira chamada pode demorar (cold start do modelo)

- O processamento ocorre 100% local

- A latência depende de CPU e RAM

- A temperatura controla a criatividade da resposta

### 🚀 Fase 3 — Chat Memory (Conversação Contextual)

#### 🎯 Objetivo

Permitir que o modelo lembre do contexto da conversa, possibilitando diálogos contínuos.

Introduz o conceito de memória de conversa, onde cada pergunta/resposta é armazenada e reutilizada em prompts futuros.

Isso possibilita:

- Conversas multi-turn

- Continuidade de contexto

- Experiência semelhante a um chatbot real

#### 🏗️ Arquitetura Atual (Fase 3)

````
controller
 └── AiController

service
 └── AiService

config
 └── ChatMemoryConfig
````

Fluxo atualizado:

````
Client → Controller → Service → ChatClient
                                  ↓
                             ChatMemory
                                  ↓
                               Ollama
                                  ↓
                               Response
````

#### 🧠 Como funciona a memória

Cada conversa recebe um conversationId.

Exemplo:

````yaml
conversationId = "dev-1"
````

Fluxo da conversa:

````
User: O que é Spring Boot?
AI: resposta

User: Ele usa IoC?
AI: responde considerando a pergunta anterior
````