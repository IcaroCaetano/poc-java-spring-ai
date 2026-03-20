
# POC Java Spring AI

![images.png](image/images.png)


### 📌 1️⃣ Objetivo

Demostrar a integração entre:

- Spring AI
2- Spring Boot

- Ollama

- Java 21

## 🚀 Fase 1 — Integracao com LLM Local (Ollama)
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


## 🚀 Fase 2 — Prompt Template Estruturado

### 🎯 Objetivo

- Separar System Prompt e User Prompt

- Definir comportamento do modelo

- Melhorar qualidade e consistência das respostas

- Introduzir camada de serviço

### 🏗️ Arquitetura Atual (Fase 2)

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


### ⚙️ 4️⃣ Configuração

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

## 🚀 Fase 3 — Chat Memory (Conversação Contextual)

### 🎯 Objetivo

Permitir que o modelo lembre do contexto da conversa, possibilitando diálogos contínuos.

Introduz o conceito de memória de conversa, onde cada pergunta/resposta é armazenada e reutilizada em prompts futuros.

Isso possibilita:

- Conversas multi-turn

- Continuidade de contexto

- Experiência semelhante a um chatbot real

### 🏗️ Arquitetura Atual (Fase 3)

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

### 🧠 Como funciona a memória

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

### ⚙️ Configuração

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
### 🚀 Execução

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

### 🔍 Endpoint disponível

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

### 🧪 Observações Técnicas

- A memória é mantida somente enquanto a aplicação está rodando

- Cada conversationId possui um histórico independente

- Em produção normalmente utiliza-se:
  - Redis 
  - Banco de dados 
  - Vector Store

## 🚀 Fase 4 — Streaming de Respostas

### 🎯 Objetivo

Permitir que o modelo envie a resposta gradualmente, token por token.

Isso melhora muito a experiência do usuário, pois a resposta começa a aparecer imediatamente

### 🏗️ Arquitetura Atual (Fase 4)

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

### 🧠 O que é Streaming?

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

### 🔬 Implementação

O Spring AI utiliza programação reativa com:

````
Flux<String>
````

Flux representa um fluxo contínuo de dados.


### ⚙️ Endpoint Streaming

````
POST /ai/ask-stream
````

Retorna:

````
Flux<String>
````


### 🚀 Teste via curl

````
curl -N -H "Content-Type: application/json" -X POST http://localhost:8080/ai/ask-stream -d "{\"conversationId\":\"dev-stream-1\",\"question\":\"Explique o que é Spring Boot\"}"
````

### 🔍 Fluxo de execução

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

### 🧪 Observações Técnicas

- Streaming reduz latência percebida

- Muito usado em chatbots

- Ideal para interfaces em tempo real

- Baseado em Reactive Streams

## 🚀 Fase 5 — Embeddings

### 🎯 Objetivo

Transformar texto em vetores numéricos, permitindo:

- busca semântica

- comparação de textos

- recomendação

- base para sistemas RAG


### 🧠 O que são Embeddings?

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

### 🏗️ Arquitetura Atual (Fase 5)


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

### 🚀 Testar endpoint

````
curl "http://localhost:8080/ai/embedding?text=Spring Boot Framework"

````

### 🔍 Endpoint disponível

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


### 🧪 Observações Técnicas

- Embeddings são base de sistemas de busca semântica

- São utilizados em Vector Databases

- Permitem construir sistemas RAG (Retrieval Augmented Generation)


## 🚀 Fase 6 — Tool Calling (Function Calling)
### 🎯 Objetivo

Permitir que o modelo execute funções Java da aplicação.

Com Tool Calling o LLM pode:

- executar lógica de negócio

- consultar APIs externas

- acessar serviços da aplicação

- integrar IA com sistemas reais

Isso transforma o modelo de chatbot passivo em um assistente capaz de agir.

### 🧠 O que são Tools?

Tools são métodos Java expostos para o modelo.

O LLM pode decidir quando chamá-los.

Exemplo:

Usuário pergunta:

```
Como está o clima em Fortaleza?
````

O modelo decide chamar a Tool:

````
getWeather(city="Fortaleza")
````

A aplicação executa o método Java e devolve o resultado ao modelo.

### 🏗️ Arquitetura Atual (Fase 6)

````
controller
 └── AiController

service
 └── AiService

tools
 └── WeatherTool
````

Fluxo:

````
Client
 ↓
Controller
 ↓
Service
 ↓
ChatClient
 ↓
LLM decide usar Tool
 ↓
WeatherTool
 ↓
Resposta final
````

### ⚙️ Implementação da Tool

Foi criada uma Tool responsável por retornar informações de clima.

````
WeatherTool
````

Essa Tool é exposta ao modelo usando a anotação:

````
@Tool
````

Exemplo conceitual:

````
@Tool(description = "Retorna o clima atual de uma cidade")
public String getWeather(String city)
````
### 🧪 Testar endpoint

````
POST /ai/ask-tools
````

Body:

````
{
  "question": "Como está o clima em Fortaleza?"
}
````

Fluxo interno:

````
Pergunta
 ↓
LLM identifica pergunta sobre clima
 ↓
LLM chama WeatherTool
 ↓
WeatherTool retorna resultado
 ↓
LLM gera resposta final
````

### 🧪 Observações Técnicas

- Nem todos os modelos suportam Tool Calling

- O modelo llama3 não suporta tools no Ollama

- Foi necessário utilizar um modelo compatível (ex: qwen2.5)

- Descrições das tools são importantes para o modelo decidir quando usá-las


## 🚀 Fase 7 — Structured Output

### 🎯 Objetivo

Permitir que o modelo retorne objetos Java estruturados ao invés de texto livre.

Isso elimina a necessidade de parsing manual de respostas do LLM.

### 🧠 Problema resolvido

Normalmente o modelo retorna texto:


````

O clima em Fortaleza está com 29°C e ensolarado.
````
Mas aplicações precisam de dados estruturados:

````
city = Fortaleza
temperature = 29
condition = Ensolarado
````

Structured Output resolve esse problema.

### 🏗️ Arquitetura Atual (Fase 7)

````
controller
 └── AiController

service
 └── AiService

dto
 └── WeatherResponse
````

Fluxo:

````
Client
 ↓
Controller
 ↓
Service
 ↓
ChatClient
 ↓
LLM gera JSON
 ↓
Spring AI converte JSON
 ↓
DTO Java
````

### ⚙️ DTO Utilizado

Foi criado um DTO para mapear a resposta do modelo.

````
WeatherResponse
````

Campos:

````

city
temperature
condition
````

O Spring AI converte automaticamente o JSON retornado pelo modelo para o DTO.

### 🧪 Endpoint disponível

````
POST /ai/weather-structured
````

Resposta:

````
{
  "city": "Fortaleza",
  "temperature": 29,
  "condition": "Ensolarado"
}
````

### 🧪 Observações Técnicas

- Structured Output utiliza conversão automática com Jackson

- O modelo precisa gerar JSON válido

- DTOs facilitam integração com serviços e bancos de dados

- Esse padrão é amplamente utilizado em sistemas corporativos com IA

## 🚀 Fase 8 — Guardrails

### 🎯 Objetivo

Implementar camadas de segurança e controle sobre as interações com o modelo.

Guardrails permitem:

- bloquear perguntas inadequadas

- validar respostas do modelo

- aplicar políticas de segurança

- reduzir risco de alucinações

### 🧠 Por que Guardrails são necessários?

LLMs podem gerar:

- conteúdo inadequado

- respostas incorretas

- informações sensíveis

Guardrails adicionam controle e proteção ao sistema.


### 🏗️ Arquitetura Atual (Fase 8)

````
controller
 └── AiController

service
 └── AiService

guardrails
 └── InputGuardrail
````

Fluxo:

````
Client
 ↓
Controller
 ↓
Service
 ↓
InputGuardrail (validação)
 ↓
ChatClient
 ↓
LLM
 ↓
Resposta
````

### ⚙️ Implementação

Foi criada uma classe responsável por validar perguntas do usuário.

````
InputGuardrail
````

Esse componente analisa a pergunta antes de enviá-la ao modelo.

Exemplo de regras:

- bloquear termos relacionados a hacking

- impedir perguntas vazias

- aplicar políticas de segurança

#### 🧪 Exemplo de pergunta bloqueada

````
Como invadir um sistema?
````

Resposta:

````
Pergunta bloqueada por política de segurança.
````

#### 🧪 Exemplo de pergunta permitida

````
🧪 Exemplo de pergunta permitida
````
Resposta:

````
Resposta gerada normalmente pelo modelo.
````
#### 🧪 Observações Técnicas

Guardrails podem ser aplicados em duas camadas:

Input Guardrails
Validação antes da chamada ao modelo.

Output Guardrails
Validação da resposta do modelo.

Tipos comuns em produção:

- Content Safety

- Policy Enforcement

- Hallucination Detection

- Output Validation

## 🚀 Fase 9 — AI Agents

### 🎯 Objetivo

Introduzir o conceito de AI Agents, permitindo que o modelo utilize ferramentas externas para resolver problemas.

Agents permitem que o modelo:

- execute cálculos

- consulte serviços externos

- combine múltiplas ferramentas

- resolver tarefas mais complexas

### 🧠 O que são AI Agents?

Um AI Agent é um modelo que pode:

````
pensar → decidir → executar ação → continuar a resposta
````

Ou seja, o modelo passa a ter capacidade de invocar ferramentas (Tools) durante a geração da resposta.

Exemplo de fluxo:

````
Pergunta: Quanto é 45 * 12?

LLM
 ↓
Detecta necessidade de cálculo
 ↓
Chama MathTool
 ↓
Recebe resultado
 ↓
Constrói resposta final
````
Isso transforma o modelo em um agente capaz de interagir com o mundo externo.

### 🏗️ Arquitetura Atual (Fase 9)

````
controller
 └── AiController

service
 └── AiService

tools
 ├── MathTool
 └── WeatherTool
````
Fluxo:

````
Client
 ↓
Controller
 ↓
Service
 ↓
ChatClient
 ↓
LLM decide usar Tool
 ↓
Tool executa ação
 ↓
LLM continua resposta
````

### ⚙️ Implementação

Foram criadas duas ferramentas que podem ser chamadas pelo modelo.

````
MathTool
WeatherTool
````

Essas ferramentas são registradas no ChatClient para que o modelo possa utilizá-las.

Exemplo simplificado:

````
chatClient.prompt()
    .user(question)
    .tools(mathTool, weatherTool)
    .call()
    .content();
````

### 🧪 Exemplo de uso

Pergunta enviada:

````
Quanto é 45 * 12?
````

Fluxo interno:

````
LLM detecta operação matemática
 ↓
MathTool é chamada
 ↓
Resultado retornado
 ↓
LLM gera resposta final
````

Resposta:

````
O resultado de 45 * 12 é 540.
````
Outro exemplo:

````
Como está o clima em Fortaleza?
````

Fluxo:

````
LLM identifica necessidade de informação externa
 ↓
WeatherTool é chamada
 ↓
Resposta construída
````

### 🧪 Observações Técnicas

AI Agents permitem:

- integração com APIs externas

- automação de tarefas

- execução de lógica de negócio

Tools podem acessar:

- bancos de dados

- APIs REST

- sistemas internos

- serviços externos

Esse padrão é amplamente utilizado em assistentes inteligentes corporativos.

## 🚀 Fase 10 — Retrieval Augmented Generation (RAG)

### 🎯 Objetivo

Permitir que o modelo responda perguntas utilizando documentos externos como fonte de conhecimento.

RAG permite:

- responder perguntas sobre documentação interna

- criar chatbots corporativos

- reduzir alucinações do modelo

- utilizar conhecimento privado da aplicação

### 🧠 O que é RAG?

RAG significa:

Retrieval Augmented Generation

O modelo não responde apenas com conhecimento próprio, mas também utilizando documentos recuperados dinamicamente.

Fluxo:

````
Retrieval Augmented Generation
````

O modelo não responde apenas com conhecimento próprio, mas também utilizando documentos recuperados dinamicamente

Fluxo:

````
Pergunta
 ↓
Embedding da pergunta
 ↓
Busca semântica no VectorStore
 ↓
Documentos relevantes
 ↓
Contexto adicionado ao prompt
 ↓
LLM gera resposta
````

### 🏗️ Arquitetura Atual (Fase 10)

````
controller
 ├── AiController
 └── RagController

service
 ├── AiService
 └── RagService

rag
 └── DocumentLoader

config
 └── RagConfig
````


Fluxo:

````
Client
 ↓
RagController
 ↓
RagService
 ↓
QuestionAnswerAdvisor
 ↓
VectorStore
 ↓
Embeddings
 ↓
LLM
 ↓
Resposta baseada nos documentos
````

### ⚙️ Implementação

Foi utilizado um Vector Store em memória para armazenar embeddings dos documentos.

````
SimpleVectorStore
````

Configuração:

````java
@Bean
public SimpleVectorStore vectorStore(EmbeddingModel embeddingModel) {
    return SimpleVectorStore.builder(embeddingModel).build();
}
````

### 📄 Carregamento de Documentos

Foi criada uma classe responsável por carregar documentos no startup da aplicação.

````java
@PostConstruct
public void loadDocuments() {
    vectorStore.add(documents);
}

````
Exemplo de documentos indexados:

````
Spring Boot é um framework Java para criação de aplicações.
Spring AI permite integrar modelos de linguagem em aplicações Spring.
Embeddings convertem texto em vetores numéricos.
````

### 🧪 Testando o RAG

Endpoint disponível:

````
GET /rag/ask?question={pergunta}
````

Exemplo:

````
GET /rag/ask?question=O que é Spring Boot?
````
Fluxo interno:

````

Pergunta
 ↓
Embedding da pergunta
 ↓
Busca semântica
 ↓
Recuperação de documentos
 ↓
Contexto injetado no prompt
 ↓
LLM gera resposta baseada nos documentos
````
RAG permite construir sistemas como:

- Chat com documentação

- Assistentes corporativos

- Sistemas de busca inteligente

Esse padrão é amplamente utilizado em aplicações modernas de IA.

### 📈 Evolução da Arquitetura da POC

A aplicação evoluiu gradualmente ao longo das fases:

````
Fase 1 — Chat básico
Fase 2 — Prompt Template
Fase 3 — Chat Memory
Fase 4 — Streaming
Fase 5 — Embeddings
Fase 6 — Tool Calling
Fase 7 — Structured Output
Fase 8 — Guardrails
Fase 9 — AI Agents
Fase 10 — RAG (Retrieval Augmented Generation)
````

### 🔬 Conceitos explorados na POC

Durante a implementação foram explorados:

- ChatClient

- Prompt Engineering

- Chat Memory

- Streaming de respostas

- Embeddings

- Tool Calling

- Structured Output

- Guardrails

- AI Agents

- Retrieval Augmented Generation (RAG)

Esses conceitos representam os principais pilares do ecossistema do Spring AI

## Fase 11 — Observability (AI Metrics)

### 🎯 Objetivo

Monitorar e analisar o comportamento das interações com o modelo de IA.

Observability permite:

- medir latência das chamadas ao modelo

- acompanhar uso de tokens

- monitorar erros e falhas

- analisar performance do sistema

- obter visibilidade do uso da IA

### 🧠 Por que Observability é importante?

Aplicações com LLM são caixas pretas por natureza.

Sem métricas, não é possível saber:

- se o modelo está lento

- quanto está sendo consumido

- onde estão os erros

- como o sistema está se comportando

Observability transforma:

````
Sistema de IA → Sistema mensurável
````

### 🏗️ Arquitetura Atual (Fase 11)

````
controller
 ├── AiController
 └── RagController

service
 ├── AiService
 └── RagService

rag
 └── DocumentLoader

tools
 ├── MathTool
 └── WeatherTool

config
 └── RagConfig

monitoring
 └── Actuator
````

Fluxo:

````
Client
 ↓
Controller
 ↓
Service
 ↓
ChatClient
 ↓
Spring AI Metrics
 ↓
Actuator
 ↓
Monitoramento
````

### ⚙️ Implementação
O Spring AI integra automaticamente com o Spring Boot Actuator.

#### 📦 Dependência
````xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
````

#### ⚙️ Configuração

````
management:
  endpoints:
    web:
      exposure:
        include: "*"

  endpoint:
    health:
      show-details: always
````

#### 🔍 Endpoints disponíveis

Health Check

````
GET /actuator/health
````
Listar métricas

````
GET /actuator/metrics/gen_ai.client.operation
````

````
GET /actuator/metrics/spring.ai.chat.client
````

````
GET /actuator/metrics/spring.ai.advisor
````

### 🧪 Observações Técnicas

As métricas coletadas incluem:

- número de chamadas ao modelo

- uso de tokens (input/output)

- tempo de execução

- uso de advisors (RAG, Memory, etc.)

Essas métricas são coletadas automaticamente pelo Spring AI.


### 📈 Benefícios

Observability permite:

- otimizar prompts

- identificar gargalos

- monitorar uso de IA

- preparar aplicação para produção

## 🚀 Fase 12 — Vector Database (PGVector)

### 🎯 Objetivo

Persistir embeddings em um banco de dados vetorial, substituindo o armazenamento em memória.

Isso permite:

- persistência dos dados

- escalabilidade

- melhor performance

- uso em produção

### 🧠 Por que usar Vector Database?

Na fase anterior foi utilizado:

````
SimpleVectorStore (memória)
````

Limitações:

- dados são perdidos ao reiniciar

- não escala

- não suporta grandes volumes

- 

### 🚀 Próximas Evoluções Possíveis

A POC pode evoluir para funcionalidades mais avançadas.


````
Fase 13 → Document Chunking (Text Splitter)
Fase 14 → Advanced RAG (Hybrid Search + Re-ranking)
Fase 15 → Multi-Agent Systems
````
