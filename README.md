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
