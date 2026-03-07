# POC Java Spring AI

## 🤖 POC — Spring AI com Ollama (Chat Simples)

### 📌 1️⃣ Objetivo

Esta Proof of Concept (POC) demonstra a integração entre:

- Spring AI
2- Spring Boot

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

A memória mantém o histórico:

````
A memória mantém o histórico:
````

#### ⚙️ Configuração

##### Bean de memória

````
ChatMemory
````

Utiliza memória (RAM) durante a execução da aplicação.

````java
@Bean
public ChatMemory chatMemory() {
    return new InMemoryChatMemory();
}
````
#### 🚀 Execução

#### 1️⃣ Subir Ollama

````
ollama run llama3
````

#### 2️⃣ Rodar aplicação

````
mvn spring-boot:run
````

#### 3️⃣ Testar endpoint com memória

````
curl -X POST http://localhost:8080/ai/ask-memory \
-H "Content-Type: application/json" \
-d '{"conversationId":"dev-1","question":"O que é Spring Boot?"}'
````

Pergunta seguinte:

````
curl -X POST http://localhost:8080/ai/ask-memory \
-H "Content-Type: application/json" \
-d '{"conversationId":"dev-1","question":"Ele usa IoC?"}'
````

#### 🔍 Endpoint disponível

````
POST /ai/ask-memory
````

Body:

````
{
  "conversationId": "dev-1",
  "question": "O que é Spring Boot?"
}
````

#### 🧪 Observações Técnicas

- A memória é mantida somente enquanto a aplicação está rodando

- Cada conversationId possui um histórico independente

- Em produção normalmente utiliza-se:
  - Redis 
  - Banco de dados 
  - Vector Store

### 🚀 Fase 4 — Streaming de Respostas

#### 🎯 Objetivo

Permitir que o modelo envie a resposta gradualmente, token por token.

Isso melhora muito a experiência do usuário, pois a resposta começa a aparecer imediatamente

#### 🏗️ Arquitetura Atual (Fase 4)

```
controller
 └── AiController

service
 └── AiService

config
 └── ChatMemoryConfig
````

Fluxo atualizado:

````

Client
  ↓
Controller
  ↓
Service
  ↓
ChatClient
  ↓
LLM Stream
  ↓
Flux<String>
  ↓
HTTP Streaming Response
````

#### 🧠 O que é Streaming?

Normalmente a LLM retorna a resposta apenas quando termina de gerar.

Streaming permite retornar:

````
Spring
 Boot
 é
 um
 framework...
````

Ou seja, cada parte da resposta chega progressivamente.

#### 🔬 Implementação

O Spring AI utiliza programação reativa com:

````
Flux<String>
````

Flux representa um fluxo contínuo de dados.


#### ⚙️ Endpoint Streaming

````
POST /ai/ask-stream
````

Retorna:

````
Flux<String>
````


#### 🚀 Teste via curl

````
curl -N -H "Content-Type: application/json" -X POST http://localhost:8080/ai/ask-stream -d "{\"conversationId\":\"dev-stream-1\",\"question\":\"Explique o que é Spring Boot\"}"
````

#### 🔍 Fluxo de execução

````
User request
     ↓
Controller
     ↓
Service
     ↓
ChatClient.stream()
     ↓
Flux<String>
     ↓
Streaming HTTP Response
````

#### 🧪 Observações Técnicas

- Streaming reduz latência percebida

- Muito usado em chatbots

- Ideal para interfaces em tempo real

- Baseado em Reactive Streams

### 🚀 Fase 5 — Embeddings
#### 🎯 Objetivo

Transformar texto em vetores numéricos, permitindo:

- busca semântica

- comparação de textos

- recomendação

- base para sistemas RAG


#### 🧠 O que são Embeddings?

Embeddings convertem texto em um vetor matemático.

Exemplo:

````

"Spring Boot"
↓
[0.123, -0.882, 0.331, ...]
````

Esses vetores representam significado semântico.

Textos semelhantes possuem vetores próximos.

Exemplo:

````
"Spring Boot framework"
"Framework Java para microservices"
````

Esses textos terão vetores próximos no espaço vetorial.

#### 🏗️ Arquitetura Atual (Fase 5)


````
controller
 ├── AiController
 └── EmbeddingController

service
 ├── AiService
 └── EmbeddingService

````

Fluxo:

````

Client
 ↓
Controller
 ↓
EmbeddingService
 ↓
EmbeddingModel
 ↓
Ollama
 ↓
Embedding Vector
````

#### ⚙️ Modelo de Embedding

Foi utilizado o modelo:

````
nomic-embed-text
````

Baixar modelo:

````
ollama pull nomic-embed-text
````

#### ⚙️ Configuração

````
application.yml
````

````
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        model: llama3
      embedding:
        model: nomic-embed-text
````

#### 🚀 Testar endpoint

````
curl "http://localhost:8080/ai/embedding?text=Spring Boot Framework"

````

#### 🔍 Endpoint disponível

````
GET /ai/embedding?text={texto}
````

Resposta:

````
[
0.021,
-0.554,
0.338,
...
]

````

Normalmente o vetor possui:

- 768 dimensões

- ou 1024 dimensões

Dependendo do modelo de embedding.


#### 🧪 Observações Técnicas

- Embeddings são base de sistemas de busca semântica

- São utilizados em Vector Databases

- Permitem construir sistemas RAG (Retrieval Augmented Generation)


#### 📈 Próximas Fases da POC

Fase 6 → Semantic Search

Fase 7 → Vector Database

Fase 8 → RAG (Retrieval Augmented Generation)

Fase 9 → Tool Calling

Fase 10 → AI Agents