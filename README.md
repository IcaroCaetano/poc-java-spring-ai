# 🤖 POC Java Spring AI  — Overview

## 🎯 Objective

This Proof of Concept demonstrates how to build an AI-powered application using:

Spring AI
Spring Boot
Ollama (local LLM)

The project evolves step by step, covering the main features required for real-world AI systems.

## 🏗️ Architecture

````
Client → Controller → Service → ChatClient → LLM (Ollama)
````

With advanced features:

````
+ Memory
+ Streaming
+ Tools (Agents)
+ RAG (Vector Store)
+ Observability
````

## 🚀 Features Implemented

### 🧠 Core AI
- Chat interaction with LLM
- Prompt engineering (system + user prompts)
- Structured responses

### 🔄 Advanced Capabilities
- Chat Memory → conversational context
- Streaming → real-time responses (Flux<String>)
- Embeddings → text → vector transformation

### 🛠️ AI Agents
- Tool/Function calling
- External logic execution (e.g., Math, Weather)

### 📚 RAG (Retrieval Augmented Generation)
- Semantic search using embeddings
- Context-aware answers based on documents
- Vector storage integration

### 🗄️ Vector Database
- PostgreSQL + PGVector
- Persistent embeddings storage
- Scalable semantic search

### 📊 Observability
- Metrics via Spring Boot Actuator
- Token usage tracking
- Request monitoring
- Performance analysis

### 🛡️ Guardrails
- Input validation
- Security rules
- Safer AI interactions

### 🧠 Key Concepts Covered
- ChatClient API
- Prompt Engineering
- Chat Memory
- Streaming (Reactive AI)
- Embeddings
- Tool Calling (Agents)
- Structured Output
- Guardrails
- RAG
- Vector Databases
- Observability


### 📈 Evolution
````
Phase 1  → Basic Chat
Phase 2  → Prompt Engineering
Phase 3  → Chat Memory
Phase 4  → Streaming
Phase 5  → Embeddings
Phase 6  → Tool Calling
Phase 7  → Structured Output
Phase 8  → Guardrails
Phase 9  → AI Agents
Phase 10 → RAG
Phase 11 → Observability
Phase 12 → Vector Database
````
