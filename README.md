# 🧠 MindForge

**MindForge** is a modular, open-source AI microservices project inspired by ChatGPT, built entirely with Spring Boot and Docker — using only free and open-source models.

## 🚀 Features

- Microservices architecture using Spring Boot
- Service discovery via Eureka
- Centralized configuration via Spring Cloud Config (Git-based)
- API Gateway for routing
- Text generation with Ollama (e.g., Mistral)
- Docker Compose support for local development
- Kubernetes-ready folder structure
- Monitoring support planned (Prometheus, Grafana)

## 📂 Project Structure

```
mind-forge/
├── eureka-server/       # Service Discovery
├── config-server/       # Central Config Server
├── api-gateway/         # API Gateway
├── text-service/        # Text generation microservice
├── image-service/       # [Planned]
├── voice-service/       # [Planned]
├── docker/              # Model containers (ollama, etc.)
├── monitoring/          # [Planned]
├── k8s/                 # Kubernetes manifests (WIP)
├── docker-compose.yml   # Local dev orchestration
└── README.md
```

## ⚙️ How to Run

1. Clone this repo:
   ```bash
   git clone https://github.com/hamzapaul-sudo/mind-forge
   cd mind-forge
   ```

2. Start with Docker Compose:
   ```bash
   docker compose up --build
   ```

   ⚠️ Before using text generation, download a model manually (e.g., mistral):
   ```bash
   docker exec -it ollama ollama pull mistral
   ```

4. Make a test call:
   ```bash
   curl -X POST http://localhost:4004/api/text/generate \
     -H "Content-Type: application/json" \
     -d '{ "model": "mistral", "prompt": "What is Java?", "stream": false }'
   ```

## 📌 Roadmap

- [x] Text service (Mistral via Ollama)
- [ ] Image generation (Stable Diffusion)
- [ ] Voice generation/transcription
- [ ] Monitoring stack
- [ ] K8s/Helm deployment
- [ ] Authentication & rate limiting

## 📄 License

MIT — feel free to use, modify, and contribute.
